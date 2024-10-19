package com.panda.cdc.dto.resp.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("用户子账号的返回参数")
public class UserRespDTO {

    @ApiModelProperty("用户ID")
    private String userId;

    @ApiModelProperty("租户ID")
    private String tenantId;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("备注")
    private String remarks;

    @ApiModelProperty("真实姓名")
    private String realName;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("职业")
    private String occupation;

    @ApiModelProperty("工号")
    private String jobNumber;

    @ApiModelProperty("用户类型（0：平台运营用户、1：租户用户、2：租户子用户）")
    private String userType;

    @ApiModelProperty("人员状态（0=在职 1=非在职）")
    private Boolean personnelStatus;

    @ApiModelProperty("启用禁用（是否失效）0启用，1禁用")
    private Integer fgDis;

    @ApiModelProperty("是否删除，0未删除，1已删除")
    private Integer fgDel;

    @ApiModelProperty("创建时间")
    private Date dtmCrt;

    @ApiModelProperty("最后更新时间")
    private Date dtmEdt;
}
