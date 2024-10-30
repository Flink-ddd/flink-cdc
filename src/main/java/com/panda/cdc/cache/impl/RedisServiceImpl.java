package com.panda.cdc.cache.impl;

import com.panda.cdc.cache.CacheService;
import com.panda.cdc.utils.CheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author muxh
 * @version V1.0
 * @since 2021-08-04 11:46
 */
@Slf4j
@Component
public class RedisServiceImpl implements CacheService {

    @Resource
    private StringRedisTemplate template;

    @Override
    public String get(String key) {
        return template.boundValueOps(key).get();
    }

    @Override
    public void set(String key, String value, long seconds) {
        template.opsForValue().set(key, value, seconds, TimeUnit.MILLISECONDS);
    }

    @Override
    public void set(String key, String value) {
        template.opsForValue().set(key, value);
    }

    @Override
    public long getSetSize(String key) {
        return template.opsForValue().size(key);
    }

    @Override
    public Long delSet(String key, String value) {
        return template.opsForSet().remove(key, value);
    }


    @Override
    public Boolean del(String key) {
        return template.delete(key);
    }

    @Override
    public Long delPrefix(String keyPrefix) {
        Set<String> keys = template.keys(keyPrefix + "*");
        return template.delete(keys);
    }

    /**
     * 递增 递增完成之后再进行设置缓存时间，确保过期时间生效
     */
    @Override
    public void setIncrement(String key, long seconds) {
        log.info("使用redis进行递增：key{},缓存时间：{}", key, seconds);
        BoundValueOperations<String, String> stringStringBoundValueOperations = template.boundValueOps(key);
        stringStringBoundValueOperations.increment(1);
        stringStringBoundValueOperations.expire(seconds, TimeUnit.MILLISECONDS);
    }

    /**
     * 递减 递增完成之后再进行设置缓存时间，确保过期时间生效
     */
    @Override
    public void setDecrement(String key) {
        log.info("使用redis进行递增：key{},缓存时间：{}", key);
        String res = this.get(key);
        if (CheckUtil.isNotEmpty(res) && Integer.valueOf(res) > 0) {
            BoundValueOperations<String, String> stringStringBoundValueOperations = template.boundValueOps(key);
            stringStringBoundValueOperations.decrement(1);
        }
//        stringStringBoundValueOperations.expire(seconds, TimeUnit.MILLISECONDS);
    }

    /**
     * 递减指定数 递减完成之后再进行设置缓存时间，确保过期时间生效
     */
    @Override
    public void setDecrementByDelta(String key, long delta) {
        log.info("使用redis进行递增：key{},缓存时间：{}", key);
        String res = this.get(key);
        if (CheckUtil.isNotEmpty(res)) {
            if (Long.valueOf(res) >= delta) {
                BoundValueOperations<String, String> stringStringBoundValueOperations = template.boundValueOps(key);
                stringStringBoundValueOperations.decrement(delta);
            } else {
                BoundValueOperations<String, String> stringStringBoundValueOperations = template.boundValueOps(key);
                stringStringBoundValueOperations.decrement(Long.valueOf(res));
            }
        }
//        stringStringBoundValueOperations.expire(seconds, TimeUnit.MILLISECONDS);
    }

    /**
     * 递增指定数 递增完成之后再进行设置缓存时间，确保过期时间生效
     */
    @Override
    public void setIncrementByDelta(String key, long delta, long seconds) {
        log.info("使用redis进行递增--setIncrementByDelta：key{},缓存时间：{}", key, seconds);
        BoundValueOperations<String, String> stringStringBoundValueOperations = template.boundValueOps(key);
        stringStringBoundValueOperations.increment(delta);
        stringStringBoundValueOperations.expire(seconds, TimeUnit.MILLISECONDS);
    }

    /**
     * 查询key的生命周期
     *
     * @param key redis 存数据的键
     * @return 默认单位秒，也可以自行传入单位
     */
    @Override
    public long getKeyExpire(String key) {
        return template.getExpire(key);
    }

    @Override
    public void set(String key, String hkey, String hvalue, long seconds) {
        BoundHashOperations<String, Object, Object> stringObjectObjectBoundHashOperations = template.boundHashOps(key);
        stringObjectObjectBoundHashOperations.expire(seconds, TimeUnit.MILLISECONDS);
        stringObjectObjectBoundHashOperations.put(hkey, hvalue);
    }

    @Override
    public String get(String key, String hkey) {
        BoundHashOperations<String, Object, Object> stringObjectObjectBoundHashOperations = template.boundHashOps(key);
        Object o = stringObjectObjectBoundHashOperations.get(hkey);
        return o == null ? null : o.toString();
    }


    @Override
    public void zSet(String key, String value, double score, Long timeOut) {
        template.opsForZSet().add(key, value, score);
        //设置过期时间
        if (null != timeOut) {
            template.expire(key, timeOut, TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public void zSetBatch(String key, List list) {
        template.opsForZSet().add(key, new HashSet<>(list));
    }


    @Override
    public Long zSetCount(String key, double min, double max) {
        return template.opsForZSet().size(key);
    }

    /**
     * score的增加or减少
     *
     * @param key
     * @param value
     * @param score
     */
    @Override
    public Double incrScore(String key, String value, double score) {
        return template.opsForZSet().incrementScore(key, value, score);
    }


    /**
     * 查询集合中指定顺序的值
     * 返回有序的集合中，score大的在前面
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    @Override
    public Set<String> revRange(String key, int start, int end) {
        return template.opsForZSet().reverseRange(key, start, end);
    }


    /**
     * Redis Hyperloglog 操作 uv
     *
     * @param key
     * @param value
     */
    @Override
    public Long pfaddUv(String key, String value, Long timeOut) {
        Long res = template.opsForHyperLogLog().add(key, value);
        if (null != timeOut) {
            //过期时间（秒）
            getExpire(key, timeOut);
        }
        return res;
    }

    /**
     * 获取日UV
     *
     * @return UV数
     */
    @Override
    public long getDailyUV(String key) {
        return template.opsForHyperLogLog().size(key);
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     */
    public boolean listSet(String key, String value, long time) {
        try {
            template.opsForList().rightPush(key, value);
            expire(key, time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                template.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //**************************************** hash ************************

    @Override
    public boolean setHash(String key, Map<String, String> map, Long timeOut) {
        try {
            template.opsForHash().putAll(key, map);
            expire(key, timeOut);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }


    /**
     * 确定hashKey是否存在
     *
     * @param key
     * @param attribute
     * @return
     */
    @Override
    public Object getHashKey(String key, String attribute) {
        return template.opsForHash().get(key, attribute);
    }

    /**
     * 确定hashKey是否存在
     *
     * @param key
     * @return
     */
    @Override
    public Set<Object> getHashObj(String key) {
        return template.opsForHash().keys(key);
    }

    @Override
    public Map<String, String> getHashValues(String key) {
        Cursor<Map.Entry<Object, Object>> cursor = template.opsForHash().scan(key, ScanOptions.scanOptions().build());
        Map<String, String> map = new HashMap<>();
        while (cursor.hasNext()) {
            Map.Entry<Object, Object> entry = cursor.next();
            map.put(entry.getKey().toString(), entry.getValue().toString());
        }
        return map;
    }

    /**
     * 确定hashKey是否存在
     *
     * @param key
     * @param attribute
     * @return ture 存在
     */
    @Override
    public Boolean isHashKey(String key, String attribute) {
        Boolean bool = template.opsForHash().hasKey(key, attribute);
        if (CheckUtil.isEmpty(bool)) {
            return true;
        }
        return bool;
    }


    /**
     * 指定属性值 重新赋值
     *
     * @param key
     * @param attribute
     * @return
     */
    @Override
    public void setHashPut(String key, String attribute, String delta, Long timeOut) {
        template.opsForHash().put(key, attribute, delta);
        expire(key, timeOut);

    }

    @Override
    public void setHashDel(String key, String attribute) {
        template.opsForHash().delete(key, attribute);
    }


    /**
     * 增加指定属性值
     *
     * @param key
     * @param attribute
     * @return
     */
    @Override
    public long setHashIncrementDelta(String key, String attribute, long delta) {
        long res = template.opsForHash().increment(key, attribute, delta);
        return res;
    }

    /**
     * 增加指定属性值指定值
     *
     * @param key
     * @param attribute
     * @return
     */
    @Override
    public long setHashIncrement(String key, String attribute, Long timeOut) {
        long res = template.opsForHash().increment(key, attribute, 1);
        expire(key, timeOut);
        return res;
    }

    /**
     * 递减指定属性值
     *
     * @param key
     * @param attribute
     * @return
     */
    @Override
    public long setHashDecrement(String key, String attribute) {
        return template.opsForHash().increment(key, attribute, -1);
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public Boolean getExpire(String key, long timeout) {
        return template.expire(key, timeout, TimeUnit.SECONDS);
    }
}
