package com.panda.cdc.dto.req.monitor;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("topic查询详情")
public class MonitorTopicDetailReqDTO {
    @ApiModelProperty(value = "租户id")
    private String tenantId;

    private String topicId;
}
