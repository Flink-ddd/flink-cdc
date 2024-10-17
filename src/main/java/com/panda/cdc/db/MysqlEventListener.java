package com.panda.cdc.db;


import com.panda.cdc.db.convert.CustomerDeserialization;
import com.panda.cdc.db.convert.DataBaseDeserialization;
import com.panda.cdc.mq.DataChangeInfo;
import com.panda.cdc.mq.DataChangeSink;
import com.ververica.cdc.connectors.mysql.source.MySqlSource;
import com.ververica.cdc.connectors.mysql.table.StartupOptions;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.apache.flink.streaming.connectors.pulsar.FlinkPulsarSink;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

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


}
