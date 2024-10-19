package com.panda.cdc.dto.req.monitor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("监控数据表的请求参数")
public class MonitorTableReqDTO {

    @ApiModelProperty(value = "租户id")
    private String tenantId;

    @ApiModelProperty(value = "主键ID")
    private String tableId;

    @ApiModelProperty(value = "所属数据库ID")
    private String databaseId;

    @ApiModelProperty(value = "监控的数据表")
    private String monitorTable;

    @ApiModelProperty(value = "备注")
    private String remark;

}
