//package com.rmpl.cdc.db;
//
//import akka.japi.tuple.Tuple3;
//import com.alibaba.fastjson.JSONObject;
//import com.rmpl.cdc.mq.ChangeDataCommonVO;
//import com.rmpl.cdc.strategy.handler.BusinessHandler;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.flink.api.common.functions.MapFunction;
//import org.apache.flink.api.common.serialization.SimpleStringSchema;
//import org.apache.flink.streaming.api.datastream.DataStream;
//import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
//import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.Map;
//import java.util.Properties;
//
///**
// * @author muxh
// * @create 2023-04-20 14:01
// * @desc kafka消费监听
// **/
//@Component
//@Slf4j
//public class KafkaEvent implements ApplicationRunner {
//
//    @Override
//    public void run(ApplicationArguments args) {
//        //创建流处理的执行环境
//        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//        Properties properties = new Properties();
//        //指定kafka的Broker地址，集群模式下逗号隔开。
//        properties.setProperty("bootstrap.servers", "127.0.0.1:9092");
////        指定zookeeper的IP，需要的话。
////        properties.setProperty("zookeeper.connect", "127.0.0.1:2181");
////        设置指定消费分组ID，如果指定的情况下
////        properties.setProperty("group.id","xxx-group");
//        //如果没有记录偏移量，第一次从最开始消费
//        properties.setProperty("auto.offset.reset", "earliest");
//        //将kafka和zookeeper配置信息加载到Flink的执行环境当中StreamExecutionEnvironment
//        FlinkKafkaConsumer<String> kafkaSource = new FlinkKafkaConsumer<>("change-data", new SimpleStringSchema(), properties);
//        //添加数据源，此处选用数据流的方式，将KafKa中的数据转换成Flink的DataStream类型
//        DataStream<String> dataStream = env.addSource(kafkaSource);
//        //打印FlinkKafkaConsumer消费的内容信息
////        dataStream.print();
//        try {
//            DataStream<Tuple3<Object, String, String>> stream = dataStream.map(new MapFunction<String, Tuple3<Object, String, String>>() {
//                private static final long serialVersionUID = 1L;
//                @Override
//                public Tuple3<Object, String, String> map(String value) throws Exception {
//                    Map<String, String> maps = (Map) JSONObject.parse(value);
//                    ChangeDataCommonVO changeDataCommonVO = new ChangeDataCommonVO();
//                    String operatorType = "";
//                    String afterData = "";
//                    for (Object key : maps.keySet()) {
//                        //基础操作数据
//                        if (key.equals("data")) {
//                            String data = String.valueOf(maps.get(key));
//                            changeDataCommonVO = JSONObject.parseObject(data, ChangeDataCommonVO.class);
//                        }
//                        //获得变更类型
//                        if (key.equals("operatorType")) {
//                            operatorType = String.valueOf(maps.get(key));
//                        }
//                        //获得变更后的数据
//                        if (key.equals("afterData")) {
//                            afterData = String.valueOf(maps.get(key));
//                        }
//                    }
//                    return new Tuple3<>(changeDataCommonVO, operatorType, afterData);
//                }
//            });
//            //调用业务实现类
//            stream.addSink(new BusinessHandler());
//            //执行任务，flink执行环境中必须要有job的执行步骤。
//            env.execute();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
