package com.panda.cdc.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * cdc-监控数据库管理表
 * </p>
 *
 * @author system
 * @since 2023-04-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("m_cdc_database")
@ApiModel(value="MCdcDatabase对象", description="cdc-监控数据库管理表")
public class MCdcDatabase implements Serializable {

    private static final long serialVersionUID=1L;

    private String id;

    @ApiModelProperty(value = "租户ID")
    @TableField("tenant_id")
    private String tenantId;

    @ApiModelProperty(value = "监控数据库的主机地址")
    @TableField("monitor_host")
    private String monitorHost;

    @ApiModelProperty(value = "监控的数据库")
    @TableField("monitor_db")
    private String monitorDb;

    @ApiModelProperty(value = "端口号")
    @TableField("monitor_port")
    private Integer monitorPort;

    @ApiModelProperty(value = "用户名")
    @TableField("monitor_username")
    private String monitorUsername;

    @ApiModelProperty(value = "密码")
    @TableField("monitor_password")
    private String monitorPassword;

    @ApiModelProperty(value = "同数据源顺序占位")
    @TableField("monitor_type")
    private Integer monitorType;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "注册来源类型（1.PC；2.小程序；3.H5；4.APP）")
    @TableField("sd_scrtp")
    private String sdScrtp;

    @ApiModelProperty(value = "注册伙伴产品(业务项目)")
    @TableField("id_scrvar")
    private String idScrvar;

    @ApiModelProperty(value = "启用禁用（是否失效）0启用，1禁用")
    @TableField("fg_dis")
    private Integer fgDis;

    @ApiModelProperty(value = "是否删除，0未删除，1已删除")
    @TableField("fg_del")
    private Integer fgDel;

    @ApiModelProperty(value = "创建用户")
    @TableField("id_usr_crt")
    private Long idUsrCrt;

    @ApiModelProperty(value = "创建时间")
    @TableField("dtm_crt")
    private Date dtmCrt;

    @ApiModelProperty(value = "最后更新用户")
    @TableField("id_usr_edt")
    private Long idUsrEdt;

    @ApiModelProperty(value = "最后更新时间")
    @TableField("dtm_edt")
    private Date dtmEdt;

}
