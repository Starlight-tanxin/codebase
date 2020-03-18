package com.zyb.mini.mall.constant;

/**
 * @author tanxin
 * @date 2019/10/31
 */
public interface PayOrderState {

    /**
     * 支付中
     */
    int PAYING = 1;
    /**
     * 支付完成
     */
    int PAY_SUCCESS = 2;
    /**
     * 支付确认完成
     */
    int PAY_FINAL = 3;


}
