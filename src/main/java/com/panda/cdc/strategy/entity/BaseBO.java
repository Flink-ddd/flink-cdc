package com.panda.cdc.strategy.entity;

import lombok.Data;

/**
 * @author muxh
 * @create 2023-04-20 14:01
 * @desc
 **/
@Data
public class BaseBO {
    /**
     * 变更前数据
     */
    private String beforeData;
    /**
     * 变更后数据
     */
    private String afterData;

    /**
     * 操作的数据
     */
    private String data;

    /**
     * 变更类型 1新增 2修改 3删除
     */
    private Integer operatorType;
    /**
     * binlog文件名
     */
    private String fileName;
    /**
     * binlog当前读取点位
     */
    private Integer filePos;
    /**
     * 数据库名
     */
    private String database;
    /**
     * 表名
     */
    private String tableName;
    /**
     * 变更时间
     */
    private Long operatorTime;
}
