package com.panda.cdc.enums;

/**
 * @author muxh
 * @create 2023-04-20 14:01
 * @desc 操作类型枚举
 **/
public enum OperatorTypeEnum {
    INSERT(1, "新增"),UPDATE(2, "修改"),DELETE(3, "删除");

    private Integer operatorType;

    private String operatorTypeName;

    OperatorTypeEnum(int operatorType, String operatorTypeName) {
        this.operatorType = operatorType;
        this.operatorTypeName = operatorTypeName;
    }

    public static String getOperatorTypeName(Integer operatorType) {
        for (OperatorTypeEnum enums : OperatorTypeEnum.values()) {
            if (enums.getOperatorType().equals(operatorType)) {
                return enums.getOperatorTypeName();
            }
        }
        return null;
    }

    public Integer getOperatorType() {
        return operatorType;
    }

    public String getOperatorTypeName() {
        return operatorTypeName;
    }
}
