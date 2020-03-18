package com.zyb.mini.mall.framework.interceptor.user;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>在需要更新用户缓存的接口出写上本注解</p>
 *
 * @author: Tx
 * @date: 2019/11/18
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UpdateUser {
    /**
     * 更新的消息
     *
     * @return
     */
    String msg() default "缓存的更新";
}
