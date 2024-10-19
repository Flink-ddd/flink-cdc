package com.panda.cdc.dto.req.monitor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("监控数据库表查询详情")
public class MonitorDetailReqDTO {
    @ApiModelProperty(value = "租户id")
    private String tenantId;

    @ApiModelProperty(value = "监控数据库的主键ID")
    private String databaseId;

    @ApiModelProperty(value = "监控数据表的主键ID")
    private String tableId;
}
