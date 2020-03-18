package com.zyb.mini.mall.constant;

/**
 * 锁的键
 *
 * @author tanxin
 * @date 2019/8/22
 */
public interface LockKey {

    /**
     * dbSet 修改时的锁
     */
    String DB_SET_UPDATE_LOCK = "DB_SET_UPDATE_";

    /**
     * dustSet 修改时的锁
     */
    String DUST_SET_UPDATE_LOCK = "DUST_SET_UPDATE_";

    /**
     * deviceSet 修改时的锁
     */
    String DEVICE_SET_UPDATE_LOCK = "DEVICE_SET_UPDATE_";

    /**
     * staffSet 修改时的锁
     */
    String STAFF_SET_UPDATE_LOCK = "STAFF_SET_UPDATE_";

    /**
     * 锁的后缀
     */
    String LOCK_KEY_SUFFIX = "_LOCK";

    /**
     * 锁过期时间,单位毫秒
     */
    long LOCK_EXPIRE_TIME = 10 * 1000L;

    /**
     * 获取锁的等待时间，单位毫秒
     */
    int GET_LOCK_TIME = 10 * 1000;

    /**
     * 获取锁的等待时间
     */
    int WAIT_LOCK_TIME = 1000;

    /**
     * 没有取到锁
     * 读取数据的等待时间
     */
    int WAIT_READ_TESTPAPER = 200;
}
