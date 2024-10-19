package com.panda.cdc.dto.req.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author muxiaohui
 */
@Data
public class AuthRequestReqDTO {

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("用户类型（0：平台运营用户、1：租户用户、2：租户子用户）")
    private Integer userType;
}
