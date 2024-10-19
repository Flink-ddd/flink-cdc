package com.panda.cdc.dao.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MCdcTenantsMenu implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 租户ID
     */
    private String tenantId;


    /**
     * 菜单ID
     */
    private String menuId;

    /**
     * 是否删除，0未删除，1已删除
     */
    private Integer fgDel;

}
