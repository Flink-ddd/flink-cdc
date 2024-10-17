package com.panda.cdc.db;

import com.panda.cdc.db.convert.DataBaseDeserialization;
import com.panda.cdc.mq.DataChangeInfo;
import com.panda.cdc.mq.DataChangeSink;
import com.ververica.cdc.connectors.mongodb.MongoDBSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.bson.Document;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * @author muxh
 * @desc mongodb变更监听 2.2.1版本
 **/
@Component
@Slf4j
public class MongodbEventListener implements ApplicationRunner {

    private final DataChangeSink dataChangeSink;

    public MongodbEventListener(DataChangeSink dataChangeSink) {
        this.dataChangeSink = dataChangeSink;
    }

    @Override
    public void run(ApplicationArguments args) {
        CompletableFuture.runAsync(() -> {
            StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
            env.setParallelism(1);
            //构造变更数据源（页面日志输出）
            SourceFunction<Document> mongodbSource = buildDataChangeSource();
            DataStreamSource<Document> mongoDBStream = env.addSource(mongodbSource, "mongodb-source").setParallelism(1);
            // 简单地打印从 MongoDB 监控到的数据变更
            mongoDBStream.print();
            try {
                //需要用到的时候放开注释即可
                env.execute("mongodb-stream-cdc");
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
    private SourceFunction<Document> buildDataChangeSource() {
        return MongoDBSource.<Document>builder()
                .hosts("localhost:27017")
                .databaseList("test")
                // 支持正则匹配
                .collectionList("testdb")
                .build();
    }

}
