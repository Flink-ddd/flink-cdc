package com.panda.cdc.mq;

/**
 * @author muxh
 * @create 2023-04-20 14:01
 * @desc 三参数消费者
 **/
@FunctionalInterface
public interface ThreeConsumer<K, T, V> {

    /**
     * 三参数消费者
     *
     * @param k
     * @param t
     * @param v
     * @return void
     * @author lei
     * @date 2022-09-20 14:23:42
     */
    void accept(K k, T t, V v);
}
