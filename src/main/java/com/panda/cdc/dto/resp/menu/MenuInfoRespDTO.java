package com.panda.cdc.dto.resp.menu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("角色菜单的返回参数")
public class MenuInfoRespDTO {

    @ApiModelProperty("菜单ID")
    private String menuId;

    @ApiModelProperty("菜单名称")
    private String menuName;
}
