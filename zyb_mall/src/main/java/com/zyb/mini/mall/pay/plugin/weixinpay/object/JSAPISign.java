package com.zyb.mini.mall.pay.plugin.weixinpay.object;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Created by 谭健 on 2018/12/12. 星期三. 16:36.
 * © All Rights Reserved.
 */


@Data
public class JSAPISign implements Serializable {

    private static final long serialVersionUID = -707075725812697866L;

    private String timestamp;
    private String packageInfo;
    private String signType;
    private String paySign;
    private String nonceStr;

}
