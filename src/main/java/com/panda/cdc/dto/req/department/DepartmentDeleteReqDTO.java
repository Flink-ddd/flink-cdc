package com.panda.cdc.dto.req.department;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author muxiaohui
 */
@Data
@ApiModel("删除租户组织架构请求参数")
public class DepartmentDeleteReqDTO {
    @ApiModelProperty("租户ID")
    private String tenantId;
    @ApiModelProperty("部门ID")
    private List<String> departmentIdList;
    @ApiModelProperty("用户ID")
    private List<String> userIdList;
}
