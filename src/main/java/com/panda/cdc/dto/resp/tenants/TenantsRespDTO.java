package com.panda.cdc.dto.resp.tenants;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author muxiaohui
 */
@Data
@ApiModel("租户账号列表返回参数")
public class TenantsRespDTO {

    @ApiModelProperty(value = "租户id")
    private String tenantId;

    @ApiModelProperty(value = "租户名称")
    private String tenantName;

    @ApiModelProperty(value = "租户唯一key")
    private String tenantKey;

    @ApiModelProperty(value = "appid")
    private String appId;

    @ApiModelProperty(value = "公钥")
    private String publicKey;

    @ApiModelProperty(value = "私钥")
    private String privateKey;

    @ApiModelProperty(value = "租户的联系邮箱")
    private String contactEmail;

    @ApiModelProperty(value = "租户的联系电话")
    private String contactPhone;

    @ApiModelProperty(value = "租户的地址信息")
    private String contactAddress;

    @ApiModelProperty(value = "备注")
    private String remarks;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "租户类型（1：内部业务、2：外部B端公司、3：外部C端个体）")
    @TableField("tenant_type")
    private Integer tenantType;

    @ApiModelProperty(value = "租户状态（0：待审核、1：审核通过、2：审核失败）")
    private Integer tenantStatus;

    @ApiModelProperty(value = "租户的订阅计划类型（0：免费、1：标准、2：企业）")
    private Integer planType;

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
