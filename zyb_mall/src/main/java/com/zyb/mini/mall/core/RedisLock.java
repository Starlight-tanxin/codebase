package com.zyb.mini.mall.core;

import com.google.common.base.Strings;
import com.zyb.mini.mall.constant.LockKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁，本锁并非绝对安全，在redis集群下可能存在用户1取到锁，
 * 然后redis挂了，此时切换redis主机，key还没有完全复制过去，用户在新的主机上取到了锁
 *
 * @author tanxin
 * @date 2019/4/29
 */
@Slf4j
@Component
public class RedisLock {

    @Resource(name = "redisTemplate")
    private RedisTemplate redisTemplate;

    /**
     * @param key   锁标识
     * @param value 时间戳(当前时间加过期时间) 设置的过期时间
     * @return true 取到锁
     */
    public boolean lock(String key, String value) {
        log.info("{}: 开始尝试获取锁...........key: {}  value: {}", Thread.currentThread().getName(), key, value);
        String lockKey = key + LockKey.LOCK_KEY_SUFFIX;
        boolean isLock = redisTemplate.opsForValue().setIfAbsent(lockKey, value);
        if (isLock) {
            // 设置自动过期时间 在没有手动释放锁 且获取锁逻辑失败时候的保障
            redisTemplate.expire(lockKey, LockKey.LOCK_EXPIRE_TIME, TimeUnit.MILLISECONDS);
            log.info("{}: 获取锁成功...........key: {}  value: {}", Thread.currentThread().getName(), key, value);
            return true;
        }
        // 获取上个锁，到值为null时候 算锁失效 且手动删除并获取一个新的锁
        // 时间小于现在的时间也是锁失效
        Object currentValue = redisTemplate.opsForValue().get(lockKey);
        long sysTime = System.currentTimeMillis();
        if (currentValue == null || Strings.isNullOrEmpty(currentValue.toString()) || Long.parseLong(currentValue.toString()) < sysTime) {
            unLock(key);
            log.info("{}: 存在上个锁且失效,开始重新获取锁...........key: {}  value: {}", Thread.currentThread().getName(), key, value);
            return lock(key, value);
        }
        log.info("{}: 获取锁失败...........key: {}  value: {}", Thread.currentThread().getName(), key, value);
        return false;
    }

    /**
     * @param key 锁标识
     */
    public void unLock(String key) {
        String lockKey = key + LockKey.LOCK_KEY_SUFFIX;
        try {
            log.info("{}: 开始释放锁...........key: {}", Thread.currentThread().getName(), key);
            boolean isExist = redisTemplate.hasKey(lockKey);
            if (isExist) {
                redisTemplate.delete(lockKey);
                log.info("{}: 释放锁成功...........key: {}", Thread.currentThread().getName(), key);
                return;
            }
            log.info("{}: 释放锁失败,锁不存在...........key: {}", Thread.currentThread().getName(), key);
        } catch (Exception e) {
            log.error("{}: 释放锁出现错误...........错误: {}", Thread.currentThread().getName(), e.getMessage(), e);
        }

    }
}
