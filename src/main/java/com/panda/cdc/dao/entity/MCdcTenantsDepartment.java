package com.panda.cdc.dao.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MCdcTenantsDepartment implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 部门ID
     */
    private String departmentId;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 父级部门ID(-1为当前租户的顶级部门)
     */
    private String parentId;

    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 部门等级（0：顶级部门，1：一级部门，2：二级部门，3：三级部门）
     */
    private String levelType;

    /**
     * 是否删除，0未删除，1已删除
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
