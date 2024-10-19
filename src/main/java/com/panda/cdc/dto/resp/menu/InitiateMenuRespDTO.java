package com.panda.cdc.dto.resp.menu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("平台自定义菜单的返回参数")
public class InitiateMenuRespDTO {
    @ApiModelProperty("菜单ID")
    private String menuId;

    @ApiModelProperty("父级菜单id(-1为顶级菜单)")
    private String parentId;

    @ApiModelProperty("租户ID")
    private String tenantId;

    @ApiModelProperty("菜单名称")
    private String menuName;

    @ApiModelProperty("标签锚点")
    private String menuUrl;

    @ApiModelProperty("页面所在地址")
    private String menuPath;

    @ApiModelProperty("标签样式")
    private String menuCss;

    @ApiModelProperty("菜单排序")
    private Integer menuSort;

    @ApiModelProperty("是否删除，0：否，1：是")
    private Boolean fgDel;

    @ApiModelProperty("创建用户")
    private Long idUsrCrt;

    @ApiModelProperty("创建时间")
    private Date dtmCrt;

    @ApiModelProperty("最后更新用户")
    private Long idUsrEdt;

    @ApiModelProperty("最后更新时间")
    private Date dtmEdt;

    @ApiModelProperty("记录版本号")
    private String verNo;
}
