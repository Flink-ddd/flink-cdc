package com.panda.cdc.dto.req.tenants;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author muxiaohui
 */
@Data
@ApiModel("租户账号审核结果请求参数")
public class TenantsAuditQueryDTO {
    @ApiModelProperty(value = "租户id")
    private String tenantId;
}
