package com.panda.cdc.dto.req.monitor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("监控数据库的请求参数")
public class MonitorDatabaseReqDTO {
    @ApiModelProperty(value = "租户id")
    private String tenantId;

    @ApiModelProperty(value = "监控数据库的主键ID")
    private String databaseId;

    @ApiModelProperty(value = "监控数据库的主机地址")
    private String monitorHost;

    @ApiModelProperty(value = "监控的数据库")
    private String monitorDb;

    @ApiModelProperty(value = "同数据源顺序占位  按照数据源顺序排列")
    private Integer monitorType;

    @ApiModelProperty(value = "端口号")
    private Integer monitorPort;

    @ApiModelProperty(value = "用户名")
    private String monitorUsername;

    @ApiModelProperty(value = "密码")
    private String monitorPassword;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "监控数据表")
    private List<MonitorTableReqDTO> monitoTableReqDTOList;

}
