package com.panda.cdc.dao.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * cdc-租户用户表
 * </p>
 *
 * @author muxh
 * @since 2024-07-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MCdcUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 地址
     */
    private String address;

    /**
     * 职业
     */
    private String occupation;

    /**
     * 工号
     */
    private String jobNumber;

    /**
     * 用户类型（0：平台运营用户、1：租户用户、2：租户子用户）
     */
    private String userType;

    /**
     * 人员状态（0=在职 1=非在职）
     */
    private Boolean personnelStatus;

    /**
     * 启用禁用（是否失效）0启用，1禁用
     */
    private Integer fgDis;

    /**
     * 是否删除，0未删除，1已删除
     */
    private Integer fgDel;

    /**
     * 创建时间
     */
    private Date dtmCrt;

    /**
     * 最后更新时间
     */
    private Date dtmEdt;


}
