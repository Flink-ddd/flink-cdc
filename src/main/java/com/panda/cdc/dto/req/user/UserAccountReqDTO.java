package com.panda.cdc.dto.req.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("租户子账号启用禁用请求参数")
public class UserAccountReqDTO {
    @ApiModelProperty("用户ID")
    private String userId;

    @ApiModelProperty("租户ID")
    private String tenantId;

    @ApiModelProperty("启用禁用（是否失效）0启用，1禁用")
    private Integer fgDis;
}
