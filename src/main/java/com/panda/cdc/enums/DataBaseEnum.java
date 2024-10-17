package com.panda.cdc.enums;

import lombok.Getter;

/**
 * @author muxh
 * @create 2023-04-20 14:01
 * @desc 数据库类型枚举
 **/
public enum DataBaseEnum {
    MYSQL(1), SQL_SERVER(2), HBASE(3), ORACLE(4), TIDB(5), MONGODB(6);
    private final int type;

    DataBaseEnum(int type) {
        this.type = type;
    }
}
