package com.panda.cdc.strategy.handler;

import java.io.Serializable;

/**
 * @author muxh
 * @create 2023-04-20 14:01
 * @desc 日志处理器
 **/
public interface BaseHandler<T> extends Serializable {

    /**
     * 新增处理
     *
     * @param data 数据转换后模型
     * @return void
     * @author muxh
     * @create 2023-04-20 14:01
     */
    void handleInsert(T data);

    /**
     * 修改处理
     *
     * @param data 数据转换后模型
     * @return void
     * @author muxh
     * @create 2023-04-20 14:01
     */
    void handleUpdate(T data);

    /**
     * 删除处理
     *
     * @param data 数据转换后模型
     * @return void
     * @author muxh
     * @create 2023-04-20 14:01
     */
    void handleDelete(T data);


}
