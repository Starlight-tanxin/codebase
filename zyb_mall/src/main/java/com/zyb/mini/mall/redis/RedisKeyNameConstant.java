package com.zyb.mini.mall.redis;

/**
 * @author Created by 谭健 on 2019/10/29. 星期二. 16:39.
 * © All Rights Reserved.
 */
public interface RedisKeyNameConstant {

    /**
     * 下面是微信登陆相关公用缓存
     */

    String WECHAT_TICKET_JSAPI = "WEIXIN:WECHAT_TICKET_JSAPI";
    String WECHAT_TICKET_WXCARD = "WEIXIN:WECHAT_TICKET_WXCARD";
    String WECHAT_TICKET = "WEIXIN:WECHAT_TICKET";
    String REDIS_ACCESS_TOKEN_KEY = "WEIXIN:REDIS_ACCESS_TOKEN_KEY";


    /**
     * 用户缓存相关
     */
    String REDIS_USER_KEY_PREFIX = "WEIXIN:USER_";
    String AUTH_HEADER = "Authorization";

    /**
     * 购物车相关
     */
    String SHOP_CART_BY_USER = "SHOP:CART_BY_UID_";

    /**
     * 物流信息
     */
    String EXPRESS_DATA_NO = "EXPRESS_DATA:EXPRESS_NO_";

    // 用户打卡缓存
    String USER_PUNCH_DATA = "USER_PUNCH_DATA:";

    /**
     * 用户验证吗
     */
    String USER_CODE_BY_MOBILE = "USER_CODE_BY_MOBILE:MOBILE";

    /**
     * 消息会话相关
     * 消息会话id
     */
    String MSG_SESSION_ID = "MSG_SESSION_ID";
    /**
     * 消息会话集合
     * 根据消息会话id取值
     */
    String MSG_LIST_BY_SESSION = "MSG_LIST_BY_SESSION:ID_";
}
