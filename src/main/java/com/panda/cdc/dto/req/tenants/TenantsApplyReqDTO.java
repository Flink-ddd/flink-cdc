package com.panda.cdc.dto.req.tenants;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author muxiaohui
 */
@Data
@ApiModel("租户账号申请请求参数")
public class TenantsApplyReqDTO {

    @ApiModelProperty(value = "租户id")
    private String tenantId;

    @ApiModelProperty(value = "租户名称")
    private String tenantName;

    @ApiModelProperty(value = "租户的联系邮箱")
    private String contactEmail;

    @ApiModelProperty(value = "租户的联系电话")
    private String contactPhone;

    @ApiModelProperty(value = "租户的地址信息")
    private String contactAddress;

    @ApiModelProperty(value = "备注")
    private String remarks;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "租户类型（0：平台运营、1：内部业务、2：外部B端公司、3：外部C端个体）")
    private Integer tenantType;

    @ApiModelProperty(value = "租户的订阅计划类型（0：免费、1：标准、2：企业）")
    private Integer planType;

    @ApiModelProperty(value = "是否有接口调用对接（0：否、1：是）  外部租户必须勾选该字段")
    private Integer isDock;

    @ApiModelProperty(value = "租户申请时勾选的菜单列表")
    private List<String> menuList;
}
