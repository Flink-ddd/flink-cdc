package com.panda.cdc.dto.req.monitor;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("操作topic状态的请求参数")
public class MonitorTopicStatusReqDTO {
    @ApiModelProperty(value = "租户id")
    private String tenantId;

    @ApiModelProperty(value = "topicID")
    private String topicId;

    @ApiModelProperty(value = "业务组ID")
    private String businessGroupId;

    @ApiModelProperty(value = "操作状态  0：启用、1：停用")
    private Integer isOperate;
}
