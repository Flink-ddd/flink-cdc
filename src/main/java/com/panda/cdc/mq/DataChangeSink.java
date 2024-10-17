package com.panda.cdc.mq;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author muxh
 * @create 2023-04-20 14:01
 * @desc
 **/
@Slf4j
@Component
public class DataChangeSink implements SinkFunction<DataChangeInfo>, Serializable {

    @Override
    public void invoke(DataChangeInfo value, Context context) {
        long exportStartTime = System.currentTimeMillis();
        log.info("当前监听的数据库：{}，数据表：{}，变更类型：{}，Binlog操作日志的信息：{}", value.getDatabase(), value.getTableName(), value.getOperatorType(), JSON.toJSONString(value.getFileName()));
        log.info("收到变更原始数据：{}", value.getBeforeData());
        log.info("收到变更后的数据：{}", value.getAfterData());
        log.info("监听到数据库变更的时长：{}", (System.currentTimeMillis() - exportStartTime) + "ms");
        //调用业务逻辑
    }
}
