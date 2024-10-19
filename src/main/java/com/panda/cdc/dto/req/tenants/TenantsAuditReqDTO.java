package com.panda.cdc.dto.req.tenants;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author muxiaohui
 */
@Data
@ApiModel("租户账号审核请求参数")
public class TenantsAuditReqDTO {
    @ApiModelProperty(value = "租户id")
    @NotNull
    private String tenantId;

    @ApiModelProperty(value = "租户名称")
    private String tenantName;

    @ApiModelProperty(value = "审核状态（1：审核通过、2：审核失败）")
    private Integer auditStatus;

    @ApiModelProperty(value = "审核备注")
    private String auditRemarks;
}
