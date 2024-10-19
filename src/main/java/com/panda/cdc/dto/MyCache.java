package com.panda.cdc.dto;

import lombok.Data;

/**
 * @author muxh
 * @des 自定义缓存实体类
 */
@Data
public class MyCache {

    /**
     * 键
     */
    private String key;

    /**
     * 值
     */
    private Object value;

    /**
     * 过期时间
     */
    private Long expireTime;


}