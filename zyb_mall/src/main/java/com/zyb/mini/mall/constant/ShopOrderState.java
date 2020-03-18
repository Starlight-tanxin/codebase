package com.zyb.mini.mall.constant;

/**
 * <p>订单状态</p>
 *
 * @author: Tx
 * @date: 2019/11/2
 */
public interface ShopOrderState {

    /**
     * 待支付
     */
    int NOT_PAY = 1;

    /**
     * 待发货 (可申请退款)
     */
    int NOT_SEND_GOODS = 2;

    /**
     * 待收获 （可申请退款）
     */
    int NOT_PULL_GOODS = 3;

    /**
     * 待评价
     */
    int NOT_EVA = 4;

    /**
     * 已评价
     */
    int EVA = 5;

    /**
     * 取消订单
     */
    int CANCEL_ORDER = 6;

    /**
     * 申请退款
     */
    int APPLY_REFUND = 7;
    /**
     * 已退款
     */
    int REFUND = 0;
}
