package com.panda.cdc.mq;

import lombok.Data;

import java.util.List;

/**
 * @author muxiaohui
 */
@Data
public class ChangeDataVO {
    /**
     * 操作的基础数据
     */
    private ChangeDataCommonVO data;

    /**
     * 变更类型 1新增 2修改 3删除
     */
    private String operatorType;

    /**
     * 变更前数据
     */
    private String beforeData;

    /**
     * 变更后数据
     */
    private List<String> afterData;

}
