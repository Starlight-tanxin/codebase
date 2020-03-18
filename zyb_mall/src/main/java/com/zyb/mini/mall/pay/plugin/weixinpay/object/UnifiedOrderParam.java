package com.zyb.mini.mall.pay.plugin.weixinpay.object;

import com.zyb.mini.mall.utils.UUID;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Created by 谭健 on 2018/12/12. 星期三. 14:48.
 * © All Rights Reserved.
 */


@Data
public class UnifiedOrderParam implements Serializable {


    private static final long serialVersionUID = 8184840434095099870L;
    private String body;
    private String outTradeNo;
    private String totalFee;
    private String spbillCreateIp;
    private String tradeType;
    private String openid;
    private String attach;
    private String deviceInfo;
    private String feeType;

    private String notifyUrl;



    public UnifiedOrderParam() {
        // 默认人名币
        this.feeType = "CNY";
        this.deviceInfo = "WEB";
        //NO_CHECK：不校验真实姓名
        //FORCE_CHECK：强校验真实姓名
        this.attach = UUID.absolutelyUniqueId();
    }
}
