package com.panda.cdc.dto.req.menu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("平台自定义菜单的查询参数")
public class InitiateMenuQueryDTO {
    @ApiModelProperty("菜单ID")
    private String menuId;

    @ApiModelProperty("租户ID")
    private String tenantId;

    @ApiModelProperty("菜单名称")
    private String menuName;

    @ApiModelProperty("页码")
    private Integer pageNum;

    @ApiModelProperty("每页数据条数")
    private Integer pageSize;
}
