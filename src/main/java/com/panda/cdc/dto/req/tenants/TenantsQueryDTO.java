package com.panda.cdc.dto.req.tenants;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author muxiaohui
 */
@Data
@ApiModel("租户账号列表的查询参数")
public class TenantsQueryDTO {

    @ApiModelProperty(value = "租户名称")
    private String tenantName;

    @ApiModelProperty("页码")
    private Integer pageNum;

    @ApiModelProperty("每页数据条数")
    private Integer pageSize;
}
