package com.panda.cdc.dto.req.menu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author muxiaohui
 */
@Data
@ApiModel("租户角色菜单树配置的请求参数")
public class TenantsConfigReqDTO {
    @ApiModelProperty("租户ID")
    private String tenantId;

    @ApiModelProperty("角色ID")
    private String roleId;

    @ApiModelProperty("菜单ID")
    private List<String> menuId;
}
