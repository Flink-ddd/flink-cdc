package com.panda.cdc.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author muxiaohui
 */
@Data
@ApiModel("顶层请求参数请求参数")
public class CommonReqDTO {

    @ApiModelProperty(value = "租户id")
    private String tenantId;

    @ApiModelProperty(value = "租户名称")
    private String tenantName;

    @ApiModelProperty(value = "租户类型（0：平台运营、1：内部业务、2：外部B端公司、3：外部C端个体）")
    private Integer tenantType;
}
