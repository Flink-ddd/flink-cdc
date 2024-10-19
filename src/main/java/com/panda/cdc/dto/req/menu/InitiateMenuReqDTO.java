package com.panda.cdc.dto.req.menu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("平台自定义菜单的请求参数")
public class InitiateMenuReqDTO {
    @ApiModelProperty("菜单ID")
    private String menuId;

    @ApiModelProperty("父级菜单id(-1为顶级菜单)")
    private String parentId;

    @ApiModelProperty("租户ID")
    private String tenantId;

    @ApiModelProperty("菜单名称")
    private String menuName;

    @ApiModelProperty("标签锚点")
    private String menuUrl;

    @ApiModelProperty("页面所在地址")
    private String menuPath;

    @ApiModelProperty("标签样式")
    private String menuCss;

    @ApiModelProperty("菜单排序")
    private Integer menuSort;
}
