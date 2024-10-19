package com.panda.cdc.dto.req.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("角色查询参数")
public class RoleQueryDTO {
    @ApiModelProperty("租户ID")
    private String tenantId;

    @ApiModelProperty("角色ID")
    private String roleId;

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("页码")
    private Integer pageNum;

    @ApiModelProperty("每页数据条数")
    private Integer pageSize;
}
