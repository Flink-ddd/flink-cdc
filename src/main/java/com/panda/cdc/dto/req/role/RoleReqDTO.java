package com.panda.cdc.dto.req.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("角色的请求参数")
public class RoleReqDTO {
    @ApiModelProperty("角色ID")
    private String roleId;

    @ApiModelProperty("租户ID")
    private String tenantId;

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("角色描述")
    private String roleDesc;

    @ApiModelProperty("角色类型（0：平台租户角色、1：租户子账号管理员角色、2：租户子账号普通用户角色）")
    private Long roleType;
}
