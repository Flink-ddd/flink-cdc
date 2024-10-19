package com.panda.cdc.dao.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MCdcTenantsDepartmentUser implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 部门ID
     */
    private String departmentId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 是否删除，0：否，1：是
     */
    private Integer fgDel;


}
