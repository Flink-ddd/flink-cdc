package com.panda.cdc.db.convert;


import com.panda.cdc.enums.DataBaseEnum;
import com.panda.cdc.mq.DataChangeInfo;
import com.panda.cdc.utils.ReadInfoUtil;
import com.ververica.cdc.debezium.DebeziumDeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.util.Collector;
import org.apache.kafka.connect.source.SourceRecord;

/**
 * @author muxh
 * @desc 通用消息读取自定义序列化
 **/
public class DataBaseDeserialization implements DebeziumDeserializationSchema<DataChangeInfo> {

    /**
     * 反序列化数据,转为变更JSON对象
     *
     * @param sourceRecord
     * @param collector
     * @return void
     * @author muxh
     * @create 2023-04-20 14:01
     */
    @Override
    public void deserialize(SourceRecord sourceRecord, Collector<DataChangeInfo> collector) {
        DataChangeInfo dataChangeInfo = ReadInfoUtil.getDataChangeInfo(sourceRecord, DataBaseEnum.MYSQL);
        collector.collect(dataChangeInfo);
    }


    @Override
    public TypeInformation<DataChangeInfo> getProducedType() {
        return TypeInformation.of(DataChangeInfo.class);
    }
}
