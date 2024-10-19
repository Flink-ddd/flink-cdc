package com.panda.cdc.dto.req.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("角色删除的请求参数")
public class RoleDeleteReqDTO {
    @ApiModelProperty("角色ID")
    private List<String> roleIds;

    @ApiModelProperty("租户ID")
    private String tenantId;
}
