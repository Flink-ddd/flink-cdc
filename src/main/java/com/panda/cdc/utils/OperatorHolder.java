package com.panda.cdc.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.ttl.TransmittableThreadLocal;
import com.panda.cdc.mq.DataChangeInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * @author muxh
 */
@Slf4j
public class OperatorHolder {

    public static final TransmittableThreadLocal<DataChangeInfo> userThreadLocal = new TransmittableThreadLocal<>();

    public static final TransmittableThreadLocal<String> threadLocal = new TransmittableThreadLocal<>();


    public static void set(DataChangeInfo localCache) {
        userThreadLocal.set(localCache);
    }

    public static void set(String localCache) {
        threadLocal.set(localCache);
    }

    public static void unset() {
        log.info("userThreadLocal.remove*******:{}", JSON.toJSONString(userThreadLocal));
        if(CheckUtil.isNotEmpty(userThreadLocal)){
            log.info("userThreadLocal.remove 删除:{}",JSON.toJSONString(userThreadLocal.get()));
            userThreadLocal.remove();
        }
    }

    public static DataChangeInfo get() {
        return userThreadLocal.get();
    }

    public static String getToken() {
        return threadLocal.get();
    }
}
