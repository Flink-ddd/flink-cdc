package com.panda.cdc.mq;

import lombok.Data;

/**
 * TODO
 *
 * @author muxh
 * @version V1.0
 * @since 2023-04-24 15:23
 */
@Data
public class IndexMessageVO {

    /**
     * ID
     */
    private String id;

    /**
     * 内容
     */
    private String content;

    /**
     * 索引名称
     */
    private String index;

    /**
     * 索引类型
     */
    private String type;
}
