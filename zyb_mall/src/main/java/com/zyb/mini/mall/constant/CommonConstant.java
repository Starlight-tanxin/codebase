package com.zyb.mini.mall.constant;

import java.math.BigDecimal;

/**
 * @author: Tx
 * @date: 2019/11/7
 */
public interface CommonConstant {

    /**
     * 正式环境
     */
    String PROD = "prod";
    /**
     * 助力打卡1元相当于几次打卡
     */
    BigDecimal MONEY_COUNT_PUNCH = BigDecimal.valueOf(2);
    /**
     * 账户金额支付表示
     */
    String ACCOUNT_PAY_MARK = "ACCOUNT_PAY";
    /**
     * 提现的表示
     */
    String CASH_MAKE = "CASH_PAY";
    /**
     * 退款标识
     */
    String REFUND_MAKE = "REFUND_MAKE";
    /**
     * 微信成功标识
     */
    String SUCCESS = "SUCCESS";
    /**
     * 快递费默认20元
     */
    BigDecimal DEFAULT_EXPRESS_PRICE = BigDecimal.valueOf(20);

    /**
     * 退款的比重
     * 最大1
     */
    BigDecimal REFUND_PROPORTION = BigDecimal.valueOf(0.95);
    /**
     * 元转分
     */
    BigDecimal FEN_2_YUAN = BigDecimal.valueOf(100);

    /**
     * 鉴赏金额分成比例
     */
    BigDecimal IDENTIFY_AMOUNT_PERCENTAGE = BigDecimal.valueOf(0.5);


    /**
     * 消息过期时间 (20天)
     */
    long MSG_TIME_OUT = 20 * 24 * 60 * 60;


    String COMPANY_IMG = "https://hnsxyts.oss-cn-zhangjiakou.aliyuncs.com/icon/httxtp.jpg";
}
