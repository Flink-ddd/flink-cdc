package com.panda.cdc.db;


import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.panda.cdc.db.convert.CustomerDeserialization;
import com.panda.cdc.db.convert.DataBaseDeserialization;
import com.panda.cdc.mq.DataChangeInfo;
import com.panda.cdc.mq.DataChangeSink;
import com.panda.cdc.utils.KafkaUtils;
import com.ververica.cdc.connectors.mysql.source.MySqlSource;
import com.ververica.cdc.connectors.mysql.table.StartupOptions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.apache.flink.streaming.connectors.pulsar.FlinkPulsarSink;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;


/**
 * @author muxh
 * @desc mysql变更监听 2.2.1版本
 **/
@Component
@Slf4j
public class MysqlEventListener implements ApplicationRunner {

    private final DataChangeSink dataChangeSink;

    public MysqlEventListener(DataChangeSink dataChangeSink) {
        this.dataChangeSink = dataChangeSink;
    }

    @Override
    public void run(ApplicationArguments args) {
        CompletableFuture.runAsync(() -> {
            StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
            env.setParallelism(1);
            //构造变更数据源（页面日志输出）
            MySqlSource<DataChangeInfo> mySqlSource = buildDataChangeSource();
            DataStream<DataChangeInfo> streamSource = env.fromSource(mySqlSource, WatermarkStrategy.noWatermarks(), "mysql-source").setParallelism(1);
            streamSource.addSink(dataChangeSink);
            //构造变更数据源（推送至业务kafka）123.57.204.93:9092,47.94.136.7:9092
            MySqlSource<String> debeziumSourceFunction = buildDataChangeSourceToString();
            DataStreamSource<String> stringDataStreamSource = env.fromSource(debeziumSourceFunction, WatermarkStrategy.noWatermarks(), "mysql-source").setParallelism(1);
            stringDataStreamSource.addSink(getKafkaProducer("127.0.0.1:9092", "change-data"));
            try {
                env.execute("mysql-stream-cdc");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).exceptionally(ex -> {
            ex.printStackTrace();
            return null;
        });
    }

    /**
     * 构造变更数据源（页面日志输出）
     *
     * @param
     * @return DebeziumSourceFunction<DataChangeInfo>
     * @author muxh
     * @create 2023-04-20 14:01
     */
    private MySqlSource<DataChangeInfo> buildDataChangeSource() {
        return MySqlSource.<DataChangeInfo>builder().hostname("127.0.0.1").port(3306).databaseList("order_center", "pay_center")
                // 支持正则匹配
                .tableList("^(order_center.m_order_address_info)", "^(pay_center.m_pay).*")
                .username("root").password("625211234")
                /**
                 * initial初始化快照，即全量导入后增量导入(检测更新数据写入)
                 * latest:只进行增量导入(不读取历史变化)
                 * timestamp:指定时间戳进行数据导入(大于等于指定时间错读取数据)
                 * 注：点击查看源码发现目前只支持initial以及latest了
                 */
//                .startupOptions(StartupOptions.initial())
                .startupOptions(StartupOptions.latest())
                //自定义序列化格式
                .deserializer(new DataBaseDeserialization()).serverTimeZone("GMT+8").build();
    }

    /**
     * 构造变更数据源（推送至业务kafka或者pulsar）
     *
     * @param
     * @return MySqlSource<String>
     * @author muxh
     */
    private MySqlSource<String> buildDataChangeSourceToString() {
        //可以从配置中读取
        return MySqlSource.<String>builder().hostname("127.0.0.1").port(3306).databaseList("order_center", "pay_center")
                // 支持正则匹配
                .tableList("^(order_center.m_order_address_info)", "^(pay_center.m_pay).*")
                // .tableList("order_center.m_order")
                .username("root").password("625211234").startupOptions(StartupOptions.latest())
                //自定义序列化格式
                .deserializer(new CustomerDeserialization()).build();
    }

    /**
     * 创建kafka生产者模式
     */
    public static FlinkKafkaProducer<String> getKafkaProducer(String brokers, String topic) {
        return new FlinkKafkaProducer<String>(brokers, topic, new SimpleStringSchema());
    }

    /**
     * 创建pulsar生产者模式
     */
    public static FlinkPulsarSink<String> getPulsarProducer(String serviceUrl, String adminUrl, Optional<String> topicOp) {
        //创建pulsar source
        Properties properties = new Properties();
        properties.setProperty("topic", String.valueOf(topicOp));
        properties.setProperty("pulsar.producer.blockIfQueueFull", "true");
        return new FlinkPulsarSink<String>(serviceUrl, adminUrl, topicOp, properties, null);
    }

    public static class DynamicKafkaSink extends RichMapFunction<String, String> {
        private transient FlinkKafkaProducer<String> kafkaProducer;
        private String brokers;

        public DynamicKafkaSink(String brokers) {
            this.brokers = brokers;
        }

        @Override
        public void open(Configuration parameters) throws Exception {
            Properties properties = new Properties();
            properties.setProperty("bootstrap.servers", brokers);
            this.kafkaProducer = new FlinkKafkaProducer<>(
                    // 空的 topic 名称，将在处理每条记录时动态设置
                    "",
                    new SimpleStringSchema(),
                    properties
            );
        }

        @Override
        public String map(String value) throws Exception {
            JSONObject jsonObjectInfo = JSONObject.parseObject(value);
            String topicName = String.valueOf(jsonObjectInfo.get("topicName"));
            String[] topicNames = topicName.split("\\,");

            int port = 9092;
            if (StringUtils.isBlank(brokers) && StringUtils.contains(brokers, ":")){
                brokers = StringUtils.split(topicName, ":")[0];
                port = Integer.parseInt(StringUtils.split(topicName, ":")[1]);
            }
            KafkaUtils.KafkaStreamServer kafkaStreamServer = KafkaUtils.bulidServer().createKafkaStreamServer(brokers,port);
            for(String tn : topicNames){
                kafkaStreamServer.sendMsg(tn, value);
                System.out.println("Message sent to Kafka topic: " + tn);
            }
            return value;
        }

        @Override
        public void close() throws Exception {
            if (kafkaProducer != null) {
                kafkaProducer.close();
            }
        }

    }

    /**
     * 静态内部类实现 MapFunction
     */
    public static class MyMapFunction extends RichMapFunction<String, String> {
        private static final String JDBC_URL = "jdbc:mysql://172.16.52.99:3306/cdc_center?autoReconnect=true";
        private static final String JDBC_USER = "root";
        private static final String JDBC_PASSWORD = "nihao123";
        private transient Connection connection;
        private final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public void open(Configuration parameters) throws Exception {
            // 初始化 JDBC 连接
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        }

        @Override
        public String map(String s) throws Exception {
            JSONObject jsonObjectInfo = JSONObject.parseObject(s);
            JsonNode node = objectMapper.readTree(s);
            String jsonNodeString = node.toString();
            JsonObject jsonObject = JsonParser.parseString(jsonNodeString).getAsJsonObject();
            String me = jsonObject.get("data").toString();
            JsonObject meO = JsonParser.parseString(me).getAsJsonObject();
            String tableName = meO.get("table").getAsString();

            // 使用 PreparedStatement 进行查询
            String query = "SELECT topic_name FROM m_cdc_topic WHERE monitor_table = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, tableName);
            ResultSet resultSet = preparedStatement.executeQuery();
            String topic_name = null;
            if (resultSet.next()) {
                topic_name = resultSet.getString("topic_name");
            }
            jsonObjectInfo.put("topicName", topic_name);
            resultSet.close();
            preparedStatement.close();
            return jsonObjectInfo.toJSONString();
        }

        @Override
        public void close() throws Exception {
            // 关闭 JDBC 连接
            if (connection != null) {
                connection.close();
            }
        }

    }


}
