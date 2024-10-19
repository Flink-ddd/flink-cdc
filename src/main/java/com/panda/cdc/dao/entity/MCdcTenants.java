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
@TableName("m_cdc_tenants")
@ApiModel(value="MCdcTenants对象", description="cdc-SaaS租户表")
public class MCdcTenants implements Serializable {
    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "租户id")
    @TableField("tenant_id")
    private String tenantId;

    @ApiModelProperty(value = "租户名称")
    @TableField("tenant_name")
    private String tenantName;

    @ApiModelProperty(value = "租户唯一key")
    @TableField("tenant_key")
    private String tenantKey;

    @ApiModelProperty(value = "appid")
    @TableField("appid")
    private String appId;

    @ApiModelProperty(value = "公钥")
    @TableField("public_key")
    private String publicKey;

    @ApiModelProperty(value = "私钥")
    @TableField("private_key")
    private String privateKey;

    @ApiModelProperty(value = "租户的联系邮箱")
    @TableField("contact_email")
    private String contactEmail;

    @ApiModelProperty(value = "租户的联系电话")
    @TableField("contact_phone")
    private String contactPhone;

    @ApiModelProperty(value = "租户的地址信息")
    @TableField("contact_address")
    private String contactAddress;

    @ApiModelProperty(value = "备注")
    @TableField("remarks")
    private String remarks;

    @ApiModelProperty(value = "真实姓名")
    @TableField("real_name")
    private String realName;

    @ApiModelProperty(value = "租户类型（0：平台运营、1：内部业务、2：外部B端公司、3：外部C端个体）")
    @TableField("tenant_type")
    private Integer tenantType;

    @ApiModelProperty(value = "租户状态（0：待审核、1：审核通过、2：审核失败）")
    @TableField("tenant_status")
    private Integer tenantStatus;

    @ApiModelProperty(value = "租户的订阅计划类型（0：免费、1：标准、2：企业）")
    @TableField("plan_type")
    private Integer planType;

    @ApiModelProperty(value = "是否有接口调用对接（0：否、1：是）")
    @TableField("is_dock")
    private Integer isDock;

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
