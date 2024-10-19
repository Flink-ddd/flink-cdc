package com.panda.cdc.dto.resp.monitor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
@ApiModel("监控数据库的返回参数")
public class MonitorRespDTO {
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

    @ApiModelProperty(value = "注册来源类型（1.PC；2.小程序；3.H5；4.APP）")
    private String sdScrtp;

    @ApiModelProperty(value = "注册伙伴产品(业务项目)")
    private String idScrvar;

    @ApiModelProperty(value = "启用禁用（是否失效）0启用，1禁用")
    private Integer fgDis;

    @ApiModelProperty(value = "是否删除，0未删除，1已删除")
    private Integer fgDel;

    @ApiModelProperty(value = "创建用户")
    private Long idUsrCrt;

    @ApiModelProperty(value = "创建时间")
    private Date dtmCrt;

    @ApiModelProperty(value = "最后更新用户")
    private Long idUsrEdt;

    @ApiModelProperty(value = "最后更新时间")
    private Date dtmEdt;

    @ApiModelProperty(value = "监控数据表信息")
    private List<MonitorTableRespDTO> monitorTableRespDTOList;
}
