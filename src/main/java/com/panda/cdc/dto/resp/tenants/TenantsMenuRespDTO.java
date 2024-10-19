package com.panda.cdc.dto.resp.tenants;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("租户菜单的返回参数")
public class TenantsMenuRespDTO {
    @ApiModelProperty(value = "租户id")
    private String tenantId;

    @ApiModelProperty("菜单ID")
    private String menuId;

    @ApiModelProperty("菜单名称")
    private String menuName;
}
