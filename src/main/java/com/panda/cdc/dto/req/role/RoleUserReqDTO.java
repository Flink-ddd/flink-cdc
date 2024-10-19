package com.panda.cdc.dto.req.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
@Data
@ApiModel("用户角色设置请求参数")
public class RoleUserReqDTO {
    @ApiModelProperty("租户ID")
    private String tenantId;

    @ApiModelProperty("用户ID")
    private String userId;

    @ApiModelProperty("角色ID")
    private List<String> roleId;
}
