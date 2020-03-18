package com.zyb.mini.mall.constant;

/**
 * @author tanxin
 * @date 2019/10/31
 */
public interface PayOrderType {

    /**
     * 鉴赏订单
     */
    int JS_PAY = 1;

    /**
     * 修复订单
     */
    int XF_PAY = 2;

    /**
     * 书城订单
     */
    int SHOP_PAY = 3;

    /**
     * 4 - 助理得支付单
     */
    int ZL_PAY = 4;

    /**
     * 5 - 充值得支付单
     */
    int CZ_PAY = 5;

    /**
     * 6 - 提现得支付单
     */
    int TX_PAY = 6;

    /**
     * 退款的支付单
     */
    int REFUND_PAY = 7;

}
