package com.zyb.mini.mall.pay.plugin.weixinpay.object;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: Tx
 * @date: 2019/11/13
 */
@Data
public class TransfersResult implements Serializable {

    private static final long serialVersionUID = -2006904394296116629L;

    private String returnCode;
    private String returnMsg;
    // 商户appid
    private String mchAppId;
    // 商户号
    private String mchId;
    // 设备号
    private String deviceInfo;
    // 随机字符串
    private String nonceStr;
    // 业务结果
    private String resultCode;
    // 错误代码
    private String errCode;
    // 错误代码描述
    private String errCodeDes;
    // 商户订单号
    private String partnerTradeNo;
    // 微信付款单号
    private String paymentNo;
    // 付款成功时间
    private String paymentTime;

}
