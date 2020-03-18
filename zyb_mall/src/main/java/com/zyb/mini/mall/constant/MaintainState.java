package com.zyb.mini.mall.constant;

/**
 * <p>修复订单状态</p>
 *
 * @author: Tx
 * @date: 2019/11/7
 */
public interface MaintainState {
    /**
     * 创建修复订单（等待官方回复）
     */
    int CREATED = 1;
    /**
     * 官方回复（需要支付收款）
     */
    int CM_EVALUATE = 2;
    /**
     * 支付收款
     */
    int FIRST_PAYING = 3;
    /**
     * 修复完成（第二次官方回复）
     */
    int COMPLETE = 4;
    /**
     * 支付尾款（支付流程完）
     */
    int TAIL_PAYING = 5;
    /**
     * 用户评价
     */
    int EVA = 6;
}
