package com.panda.cdc.db;

import com.panda.cdc.db.convert.DataBaseDeserialization;
import com.panda.cdc.mq.DataChangeInfo;
import com.panda.cdc.mq.DataChangeSink;
import com.ververica.cdc.connectors.sqlserver.SqlServerSource;
import com.ververica.cdc.connectors.sqlserver.table.StartupOptions;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * @author muxh
 * @desc SqlServer变更监听 2.2.1版本
 **/
@Component
@Slf4j
public class SqlServerEventLister implements ApplicationRunner {

    private final DataChangeSink dataChangeSink;

    public SqlServerEventLister(DataChangeSink dataChangeSink) {
        this.dataChangeSink = dataChangeSink;
    }

    @Override
    public void run(ApplicationArguments args) {
        CompletableFuture.runAsync(() -> {
            //1.创建流处理的执行环境
            StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
            env.setParallelism(1);
            //2.Flink-CDC将读取binlog的位置信息以状态的方式保存在CK,如果想要做到断点续传,需要从Checkpoint或者Savepoint启动程序
            //2.1 开启Checkpoint,每隔5秒钟做一次CK
            env.enableCheckpointing(5000L);
            //2.2 指定CK的一致性语义
            env.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);
            //2.3 设置任务关闭的时候保留最后一次CK数据
            env.getCheckpointConfig().enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);
            //2.4 指定从CK自动重启策略
            env.setRestartStrategy(RestartStrategies.fixedDelayRestart(3, 2000L));
            //构造变更数据源（页面日志输出）
            SourceFunction<DataChangeInfo> sqlServerSource = buildDataChangeSource();
            DataStreamSource<DataChangeInfo> streamSource = env.addSource(sqlServerSource, "sqlServer-source").setParallelism(1);
            streamSource.addSink(dataChangeSink);
            try {
                //执行任务，flink执行环境中必须要有job的执行步骤。
                //需要用到的时候放开注释即可
//                env.execute("sqlServer-stream-cdc");
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
    private SourceFunction<DataChangeInfo> buildDataChangeSource() {
        return SqlServerSource.<DataChangeInfo>builder()
                .hostname("127.0.0.1")
                .port(1433)
                .database("test")
                .tableList("test.test1")
                .username("test")
                .password("test123")
                /**initial初始化快照,即全量导入后增量导入(检测更新数据写入)
                 * latest:只进行增量导入(不读取历史变化)
                 * timestamp:指定时间戳进行数据导入(大于等于指定时间错读取数据)
                 * 注：点击查看源码发现目前只支持initial以及latest了
                 */.startupOptions(StartupOptions.latest())
                //自定义序列化格式
                .deserializer(new DataBaseDeserialization()).build();
    }
}
