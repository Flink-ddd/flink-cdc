package com.panda.cdc.db;

import com.panda.cdc.constant.GlobalConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * @author muxh
 * @desc 本机执行环境开发SQL作业
 * 还有一种是基于FlinkSQLClient上开发SQL作业，常见于同构与异构数据源中的多张表进行关联写入一个目标数据源的表中。在原表中进行数据变更，目标数据源中的表数据准实时发生变化。
 **/
@Component
@Slf4j
public class FlinkSqlListener implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) {
        CompletableFuture.runAsync(() -> {
            StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
            //远程执行环境
            env.enableCheckpointing(GlobalConstant.CHECK_POINTING).setParallelism(GlobalConstant.PARA_LLE);
            //基于blink版本planner进行流处理（或者基于1.3x进行批处理，任意选型）
            EnvironmentSettings settings = EnvironmentSettings
                    .newInstance()
                    .inStreamingMode()
                    .build();
            //flink-cdc利用连接器表实现异构数据源集成，同步至elasticSearch
            StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env, settings);
            //创建MySQL cdc 订单信息表
            tableEnv.executeSql(sourceOrderDDL());
            //创建MySQL cdc 支付主表
            tableEnv.executeSql(sourcePayDDL());
            //创建sink表
            tableEnv.executeSql(sinkDDL());
            // 将order_info、pay_request同步到order_info_sink
            tableEnv.executeSql("insert into pay_order_sink " +
                    "select oi.main_order_no,oi.user_id,oi.user_name,oi.order_source,oi.purchase_type," +
                    "pi.return_no,pi.pay_channel_type,pi.pay_amount,pi.return_msg" +
                    " from order_info as oi left join pay_request as pi on oi.main_order_no = pi.main_order_no " +
                    "and oi.is_delete = 0;");
            //自定义目录名和库名
//            tableEnv.useCatalog("");
//            tableEnv.useDatabase("");
            try {
                env.execute("flink-cdc-tableEnv");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).exceptionally(ex -> {
            ex.printStackTrace();
            return null;
        });
    }

    private String sourceOrderDDL() {
        String sourceOrderDDL = "CREATE TABLE order_info (\n" +
                "  main_order_no STRING,\n" +
                "  user_id LONG,\n" +
                "  user_name STRING,\n" +
                "  order_source INTEGER,\n" +
                "  purchase_type INTEGER,\n" +
                "  PRIMARY KEY (main_order_no) NOT ENFORCED\n" +
                ") WITH (\n" +
                "   'connector' = 'mysql-cdc',\n" +
                "   'hostname' = '192.168.3.31',\n" +
                "   'port' = '3306',\n" +
                "   'username' = 'root',\n" +
                "   'password' = '******',\n" +
                "   'database-name' = 'order_center',\n" +
                "   'table-name' = 'order_info'\n" +
                ");";
        return sourceOrderDDL;
    }

    private String sourcePayDDL() {
        String sourcePayDDL = "CREATE TABLE pay_request (\n" +
                "  main_order_no STRING,\n" +
                "  return_no STRING,\n" +
                "  pay_channel_type INTEGER,\n" +
                "  pay_amount INTEGER,\n" +
                "  return_msg TEXT,\n" +
                "  PRIMARY KEY (main_order_no) NOT ENFORCED\n" +
                ") WITH (\n" +
                "   'connector' = 'mysql-cdc',\n" +
                "   'hostname' = '192.168.3.30',\n" +
                "   'port' = '3306',\n" +
                "   'username' = 'root',\n" +
                "   'password' = '******',\n" +
                "   'database-name' = 'pay_center',\n" +
                "   'table-name' = 'pay_request'\n" +
                ");";
        return sourcePayDDL;
    }

    private String sinkDDL() {
        String sinkDDL = "CREATE TABLE pay_order_sink (\n" +
                "  main_order_no STRING,\n" +
                "  order_sub_no STRING,\n" +
                "  return_no STRING,\n" +
                "  pay_channel_type INTEGER,\n" +
                "  pay_amount INTEGER,\n" +
                "  return_msg TEXT,\n" +
                "  user_id LONG,\n" +
                "  user_name STRING,\n" +
                "  order_source INTEGER,\n" +
                "  purchase_type INTEGER,\n" +
                ") WITH (\n" +
                "   'connector' = 'elasticsearch-5',\n" +
                "   'username' = 'elastic',\n" +
                "   'password' = '******',\n" +
                "   'hosts' = 'http://xx.xx.xx.xx:9200',\n" +
                "   'index' = 'pay_order_index',\n" +
                //可选的错误处理，可选择fail（抛出异常）、ignore（忽略任何错误）、retry-rejected（重试）
                "   'failure-handler' = 'ignore',\n" +
                //可选参数, 快照时不允许批量写入（flush）,默认为 true
                "   'sink.flush-on-checkpoint' = 'true',\n" +
                //可选参数, 每批次最多的条数
                "   'sink.bulk-flush.max-actions' = '100',\n" +
                //可选参数, 每批次的累计最大大小(只支持 mb)
                "   'sink.bulk-flush.max-size' = '100mb',\n" +
                //可选参数, 批量写入的间隔(ms)
                "   'sink.bulk-flush.interval' = '1000',\n" +
                //每次请求的最大超时时间(ms)
                "   'connection.max-retry-timeout' = '300',\n" +
                "   'format' = 'json',\n" +
                ");";
        return sinkDDL;
    }
}
