package com.panda.cdc.dto.req.department;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author muxiaohui
 */
@Data
@ApiModel("租户组织架构请求参数")
public class DepartmentReqDTO {
    @ApiModelProperty("部门ID")
    private String departmentId;

    @ApiModelProperty("父级部门ID(-1为当前租户的顶级部门)")
    private String parentId;

    @ApiModelProperty("租户ID")
    private String tenantId;

    @ApiModelProperty("部门名称")
    private String departmentName;

    @ApiModelProperty("部门等级（0：顶级部门，1：一级部门，2：二级部门，3：三级部门）")
    private String levelType;

    @ApiModelProperty("用户ID")
    private List<String> userId;

}
