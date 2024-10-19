package com.panda.cdc.dto.resp.menu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("租户角色的返回参数")
public class RoleInfoRespDTO {
    @ApiModelProperty("角色ID")
    private String roleId;

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("角色-菜单列表数据")
    private List<MenuInfoRespDTO> menuInfoRespDTOList;

}
