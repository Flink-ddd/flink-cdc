package com.panda.cdc.dto.req.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("角色启用禁用的请求参数")
public class RoleHandleReqDTO {

    @ApiModelProperty("角色ID")
    private String roleId;

    @ApiModelProperty("租户ID")
    private String tenantId;

    @ApiModelProperty("启用禁用（是否失效）0启用，1禁用")
    private Integer fgDis;
}
