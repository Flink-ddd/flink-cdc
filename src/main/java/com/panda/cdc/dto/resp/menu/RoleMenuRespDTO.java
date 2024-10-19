package com.panda.cdc.dto.resp.menu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("租户角色菜单树的返回参数")
public class RoleMenuRespDTO {
    @ApiModelProperty("租户ID")
    private String tenantId;

    @ApiModelProperty("租户-角色列表数据")
    private List<RoleInfoRespDTO> roleInfoRespDTOList;
}
