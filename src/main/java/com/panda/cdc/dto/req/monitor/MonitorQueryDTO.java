package com.panda.cdc.dto.req.monitor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("监控数据表查询参数")
public class MonitorQueryDTO {
    @ApiModelProperty(value = "租户id")
    private String tenantId;

    @ApiModelProperty(value = "监控数据库的主键ID")
    private String databaseId;

    @ApiModelProperty(value = "监控的数据库")
    private String monitorDb;

    @ApiModelProperty(value = "监控的数据表")
    private String monitorTable;

    @ApiModelProperty("页码")
    private Integer pageNum;

    @ApiModelProperty("每页数据条数")
    private Integer pageSize;
}
