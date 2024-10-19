package com.panda.cdc.dto.resp.department;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author muxiaohui
 */
@Data
@ApiModel("租户组织架构用户列表返回参数")
public class DepartmentUserListRespDTO {
    @ApiModelProperty("用户ID")
    private String userId;
    @ApiModelProperty("真实名称")
    private String realName;
}
