package com.zyb.mini.mall.web.controller;

import com.alibaba.fastjson.JSON;
import com.zyb.mini.mall.core.R;
import com.zyb.mini.mall.core.Status;
import com.zyb.mini.mall.pojo.entity.User;
import com.zyb.mini.mall.redis.RedisKeyNameConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 * Dream what you want to dream;
 * go where you want to go;
 * be what you want to be;
 * because you have only one life and one chance to do all the things you want to do．
 * - - Online zuozuo / Frank / TANJIAN
 *
 * @author Created by 谭健 on 2019/10/27 星期日 10:48.
 * @link <a href="http://qm.qq.com/cgi-bin/qm/qr?k=FJVK7slBx7qC5tKm_KdFTbwWOFHq1ASt">Join me</a>
 * @link <a href="http://blog.csdn.net/qq_15071263">CSDN Home Page</a>
 * <p>
 * <p>
 * © All Rights Reserved.
 */
public abstract class BaseController {

    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected StringRedisTemplate redis;

    /**
     * 获取当前登陆用户信息
     */
    protected User currentUser() {
        String s = RedisKeyNameConstant.REDIS_USER_KEY_PREFIX + request.getHeader(RedisKeyNameConstant.AUTH_HEADER);
        String userInfo = redis.opsForValue().get(s);
        if (StringUtils.isNotBlank(userInfo)) {
            return JSON.parseObject(userInfo, User.class);
        }
        return new User();
    }

    protected R success(Object data) {
        return R.success(data);
    }

    protected R success() {
        return R.success(null);
    }

    protected R fail(String msg) {
        return R.requestBad(msg);
    }
}
