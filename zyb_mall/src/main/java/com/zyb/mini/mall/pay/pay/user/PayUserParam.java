package com.zyb.mini.mall.pay.pay.user;

import lombok.Data;

/**
 * @author Created by 谭健 on 2019/10/30. 星期三. 15:07.
 * © All Rights Reserved.
 * <p>
 * 企业付款参数
 */

@Data
public class PayUserParam {


    /**
     * appId
     */
    private String appId;

    /**
     * 金额: 分
     */
    private int amount;

    /**
     * 内部订单号码
     */
    private String orderNumber;

    /**
     * openId
     */
    private String openId;

    /**
     * 描述 ： 写死的
     */
    private String desc;

    /**
     * 写死的服务器IP
     */
    private String userRealIp = "39.98.142.99";

    public PayUserParam() {
        this.desc = "提现";
    }
}
