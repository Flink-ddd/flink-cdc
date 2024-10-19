package com.panda.cdc.dto.resp.department;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author muxiaohui
 */
@Data
@ApiModel("租户组织架构查询详情返回参数")
public class DepartmentDetailRespDTO {
    @ApiModelProperty("租户ID")
    private String tenantId;
    @ApiModelProperty("租户名称")
    private String tenantName;
    @ApiModelProperty("部门列表")
    private List<DepartmentListRespDTO> departmentListRespDTOList;

}
