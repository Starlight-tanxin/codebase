package com.zyb.mini.mall.pay.plugin.weixinpay.object;

import com.zyb.mini.mall.pay.plugin.weixinpay.config.WXPayConstants;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Created by 谭健 on 2019/6/19. Wednesday. 11:06.
 * © All Rights Reserved.
 */

@Data
public class NotifyField implements Serializable {
    private static final long serialVersionUID = -4922470903989297119L;
    private String transactionId;
    private String bankType;
    private String openid;
    private String outTradeNo;
    private String totalFee;
    private String attach;
    private String notifyOriginal;


    public NotifyField(Map<String, String> map) {
        // 微信交易号
        this.transactionId = map.get("transaction_id");
        this.bankType = map.get("bank_type");
        this.openid = map.get("openid");
        // 本系统内部订单号
        this.outTradeNo = map.get("out_trade_no");
        this.totalFee = map.get("total_fee");
        // 扩展字段
        this.attach = map.get("attach");
        this.notifyOriginal = map.get(WXPayConstants.NOTIFY_ORIGINAL);
    }
}
