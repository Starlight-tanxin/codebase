package com.zyb.mini.mall.core;

import com.google.common.base.Strings;
import com.zyb.mini.mall.redis.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author tanxin
 * @date 2019/8/23
 */
@Slf4j
@Component
public class RedisCache {

    @Resource(name = "redisTemplate")
    private RedisTemplate redisTemplate;


    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    /**
     * 刷新hash内的缓存
     *
     * @param key     hash key
     * @param item    item key
     * @param jsonStr 重新设置的值
     * @param time    时间（秒）
     */
    public void refreshHashCacheByTime(String key, String item, String jsonStr, long time) {
        RedisUtils.hset(redisTemplate, key, item, jsonStr, time);
    }

    /**
     * 刷新hash内的缓存
     *
     * @param key     hash key
     * @param item    item key
     * @param jsonStr 重新设置的值
     */
    public void refreshHashCache(String key, String item, String jsonStr) {
        RedisUtils.hset(redisTemplate, key, item, jsonStr);
    }

    /**
     * 获取缓存的值
     *
     * @param key  hash key
     * @param item item key
     * @return 可能有null值
     */
    public String getHashCache(String key, String item) {
        Object obj = RedisUtils.hget(redisTemplate, key, item);
        if (obj == null || Strings.isNullOrEmpty(obj.toString())) {
            return null;
        }
        return obj.toString();
    }

    /**
     * 删除hash cache 的条目
     *
     * @param key  hash key
     * @param item item key
     */
    public void delHashItemCache(String key, String item) {
        RedisUtils.hdel(redisTemplate, key, item);
    }

    /**
     * 删除hash cache
     *
     * @param key hash key
     */
    public void delHashCahe(String key) {
        RedisUtils.del(redisTemplate, key);
    }


    /**
     * 刷新字符串缓存
     *
     * @param key     key
     * @param jsonStr 值
     */
    public void refreshStrCache(String key, String jsonStr) {
        RedisUtils.set(redisTemplate, key, jsonStr);
    }

    /**
     * 刷新字符串缓存
     *
     * @param key     key
     * @param jsonStr 值
     * @param time 时间（秒）
     */
    public void refreshStrCacheByTime(String key, String jsonStr, long time) {
        RedisUtils.set(redisTemplate, key, jsonStr, time);
    }

    /**
     * 删除字符串缓存
     *
     * @param key
     */
    public void delStrCace(String key) {
        if (RedisUtils.hasKey(redisTemplate, key)) {
            RedisUtils.del(redisTemplate, key);
        }
    }

    /**
     * 获取str 缓存
     *
     * @param key key
     * @return 可能有null值
     */
    public String getStrCache(String key) {
        return RedisUtils.getString(redisTemplate, key);
    }
}
