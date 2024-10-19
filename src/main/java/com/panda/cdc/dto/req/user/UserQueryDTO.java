package com.panda.cdc.dto.req.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("用户子账号的查询参数")
public class UserQueryDTO {

    @ApiModelProperty("用户ID")
    private String userId;

    @ApiModelProperty("租户ID")
    private String tenantId;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("页码")
    private Integer pageNum;

    @ApiModelProperty("每页数据条数")
    private Integer pageSize;
}
