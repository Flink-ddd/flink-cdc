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
 * cdc-监控数据表管理表
 * </p>
 *
 * @author system
 * @since 2023-04-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("m_cdc_database_table")
@ApiModel(value="MCdcDatabaseTable对象", description="cdc-监控数据表管理表")
public class MCdcDatabaseTable implements Serializable {

    private static final long serialVersionUID=1L;

    private String id;

    @ApiModelProperty(value = "租户ID")
    @TableField("tenant_id")
    private String tenantId;

    @ApiModelProperty(value = "所属数据库ID")
    @TableField("database_id")
    private String databaseId;

    @ApiModelProperty(value = "监控的数据表")
    @TableField("monitor_table")
    private String monitorTable;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "注册来源类型（1.PC；2.小程序；3.H5；4.APP）")
    @TableField("sd_scrtp")
    private String sdScrtp;

    @ApiModelProperty(value = "注册伙伴产品(业务项目)")
    @TableField("id_scrvar")
    private String idScrvar;

    @ApiModelProperty(value = "注册上传机构（公司名）")
    @TableField("id_scrorg")
    private String idScrorg;

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

    @ApiModelProperty(value = "记录版本号")
    @TableField("ver_no")
    private String verNo;


}
