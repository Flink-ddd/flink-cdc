package com.panda.cdc.dto.resp.tenants;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author muxiaohui
 */
@Data
@ApiModel("租户账号审核的返回参数")
public class AuditRespDTO {
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

    @ApiModelProperty(value = "租户类型（0：平台运营、1：内部业务、2：外部B端公司、3：外部C端个体）")
    private Integer tenantType;

    @ApiModelProperty(value = "租户状态（0：待审核、1：审核通过、2：审核失败）")
    private Integer tenantStatus;

    @ApiModelProperty(value = "租户的订阅计划类型（0：免费、1：标准、2：企业）")
    private Integer planType;

    @ApiModelProperty("用户名   平台审核通过后，可见此信息，后期以短信或者邮箱形式发放。")
    private String userName;

    @ApiModelProperty("密码   平台审核通过后，可见此信息，后期以短信或者邮箱形式发放。")
    private String password;

    private List<TenantsMenuRespDTO> tenantsMenuRespDTOList;


}
