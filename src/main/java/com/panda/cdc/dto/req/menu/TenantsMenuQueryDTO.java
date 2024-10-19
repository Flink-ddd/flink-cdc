package com.panda.cdc.dto.req.menu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("租户菜单列表的查询参数")
public class TenantsMenuQueryDTO {
    @ApiModelProperty("租户ID")
    private String tenantId;
}
