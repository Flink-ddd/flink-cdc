package com.panda.cdc.dto.req.monitor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("删除监控的请求参数")
public class MonitorDelReqDTO {
    @ApiModelProperty(value = "租户id")
    private String tenantId;

    @ApiModelProperty(value = "监控数据库的主键ID")
    private List<String> databaseNameList;

    @ApiModelProperty(value = "监控数据表的主键ID")
    private List<String>  tableNameList;
}
