package com.zyb.mini.mall.pay.plugin.weixinpay.object;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Created by 谭健 on 2018/12/12. 星期三. 14:11.
 * © All Rights Reserved.
 */


@Data
public class UnifiedOrderResult implements Serializable {
    private static final long serialVersionUID = -5178788813813518260L;


    private String returnCode;
    private String returnMsg;

    private String appid;
    private String mchId;
    private String nonceStr;
    private String sign;
    private String resultCode;
    private String tradeType;
    private String prepayId;

    // 不是一定有的字段
    private String deviceInfo;
    private String errCode;
    private String errCodeDes;
    private String codeUrl;


}
