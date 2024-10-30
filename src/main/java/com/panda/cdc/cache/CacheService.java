package com.panda.cdc.cache;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author muxh
 * @version V1.0
 * @since 2021-08-04 11:46
 */
public interface CacheService {

    public String get(String key);

    public void set(String key, String value, long seconds);

    public void set(String key, String value);

    long getSetSize(String key);

    Long delSet(String key, String value);

    public Boolean del(String key);

    /**
     * @Description: 根据key前缀批量删除
     * @Param: [keyPrefix]
     * @return: java.lang.Long
     * @Author: li-xin-fan
     * @Date: 2022/3/3
     */
    Long delPrefix(String keyPrefix);

    void setIncrement(String key, long second);

    void setDecrement(String key);

    void setDecrementByDelta(String key, long delta);

    void setIncrementByDelta(String key, long delta, long seconds);

    long getKeyExpire(String key);

    void set(String key, String hkey, String hvalue, long seconds);

    String get(String key, String hkey);

    void zSet(String key, String value, double score, Long timeOut);

    void zSetBatch(String key, List list);

    Long zSetCount(String key, double min, double max);

    Double incrScore(String key, String value, double score);

    Set<String> revRange(String key, int start, int end);

    Long pfaddUv(String key, String value, Long timeOut);

    long getDailyUV(String key);

    boolean setHash(String key, Map<String, String> map, Long timeOut);

    Object getHashKey(String key, String attribute);

    Set<Object> getHashObj(String key);

    Map<String, String> getHashValues(String key);

    Boolean isHashKey(String key, String attribute);

    void setHashPut(String key, String attribute, String delta, Long timeOut);

    void setHashDel(String key, String attribute);

    long setHashIncrementDelta(String key, String attribute, long delta);

    long setHashIncrement(String key, String attribute, Long timeOut);

    long setHashDecrement(String key, String attribute);
}
