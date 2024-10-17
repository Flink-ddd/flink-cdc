package com.panda.cdc.mq;

import lombok.Data;

/**
 * @author muxiaohui
 */
@Data
public class ChangeDataCommonVO {
    private String dataBase;
    private String table;
}
