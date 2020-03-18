package com.zyb.mini.mall.constant;

/**
 * <p>鉴赏状态</p>
 *
 * @author: Tx
 * @date: 2019/11/7
 */
public interface IdentifyState {

    /**
     * 创建鉴赏（本状态能够支付）
     */
    int CREATED = 1;

    /**
     * 支付完成提交鉴赏
     */
    int COMMIT = 2;

    /**
     * 鉴赏完成
     */
    int COMPLETE = 3;

    /**
     * 退回鉴赏
     */
    int BACK = 4;

    /**
     * 退回鉴赏
     * 鉴赏完成才能评价
     */
    int EVA = 5;
}
