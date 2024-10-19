package com.panda.cdc.dto.req.monitor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("自定义topic的查询参数")
public class MonitorTopicQueryDTO {
    @ApiModelProperty(value = "租户id")
    private String tenantId;

    @ApiModelProperty(value = "监控的数据库")
    private String monitorDb;

    @ApiModelProperty(value = "监控的数据表")
    private String monitorTable;

    @ApiModelProperty(value = "topic来源 1：rabbitMQ、2：rocketMQ、3：kafka")
    private Integer topicSource;

    @ApiModelProperty(value = "自定义topic名称")
    private String topicName;

    @ApiModelProperty("页码")
    private Integer pageNum;

    @ApiModelProperty("每页数据条数")
    private Integer pageSize;
}
