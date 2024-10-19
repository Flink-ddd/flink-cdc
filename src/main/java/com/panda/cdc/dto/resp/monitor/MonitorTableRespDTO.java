package com.panda.cdc.dto.resp.monitor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("监控数据表的返回参数")
public class MonitorTableRespDTO {
    @ApiModelProperty(value = "主键ID")
    private String tableId;

    @ApiModelProperty(value = "所属数据库ID")
    private Long databaseId;

    @ApiModelProperty(value = "监控的数据表")
    private String monitorTable;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "注册来源类型（1.PC；2.小程序；3.H5；4.APP）")
    private String sdScrtp;

    @ApiModelProperty(value = "注册伙伴产品(业务项目)")
    private String idScrvar;

    @ApiModelProperty(value = "注册上传机构（公司名）")
    private String idScrorg;

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

    @ApiModelProperty(value = "记录版本号")
    private String verNo;
}
