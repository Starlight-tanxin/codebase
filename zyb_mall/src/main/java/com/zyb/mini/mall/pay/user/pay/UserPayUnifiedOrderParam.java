package com.zyb.mini.mall.pay.user.pay;

import lombok.Data;

/**
 * @author Created by 谭健 on 2019/10/30. 星期三. 11:36.
 * © All Rights Reserved.
 * <p>
 * 预下单参数
 */


@Data
public class UserPayUnifiedOrderParam {

    /**
     * 商品描述
     */
    private String body;

    /**
     * 总价 单位：分
     */
    private int totalFee;

    /**
     * 用户IP
     */
    private String userRealIp;

    /**
     * 用户openid
     */
    private String openId;

    /**
     * 回传参数: 不填
     */
    private String attach;

    /**
     * 回调地址
     */
    private String notifyUrl;

    /**
     * 自我系统内的订单号
     */
    private String orderNumber;

}
