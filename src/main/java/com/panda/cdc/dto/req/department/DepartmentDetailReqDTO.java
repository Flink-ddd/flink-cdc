package com.panda.cdc.dto.req.department;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author muxiaohui
 */
@Data
@ApiModel("租户组织架构查询详情请求参数")
public class DepartmentDetailReqDTO {
    @ApiModelProperty("租户ID")
    private String tenantId;
}
