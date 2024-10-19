package com.panda.cdc.dao.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * cdc中心-SaaS菜单表
 * </p>
 *
 * @author muxh
 * @since 2024-07-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MCdcMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单ID
     */
    private String menuId;

    /**
     * 父级菜单id(-1为顶级菜单)
     */
    private String parentId;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 标签锚点
     */
    private String menuUrl;

    /**
     * 页面所在地址
     */
    private String menuPath;

    /**
     * 标签样式
     */
    private String menuCss;

    /**
     * 菜单排序
     */
    private Integer menuSort;

    /**
     * 是否删除，0：否，1：是
     */
    private Integer fgDel;

    /**
     * 创建用户
     */
    private Long idUsrCrt;

    /**
     * 创建时间
     */
    private Date dtmCrt;

    /**
     * 最后更新用户
     */
    private Long idUsrEdt;

    /**
     * 最后更新时间
     */
    private Date dtmEdt;

    /**
     * 记录版本号
     */
    private String verNo;


}
