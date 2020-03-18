package com.zyb.mini.mall.pay.plugin.weixinpay.object;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: Tx
 * @date: 2019/11/13
 */
@Data
public class TransfersParam implements Serializable {
    private static final long serialVersionUID = 4529210624910977014L;


    /**
     * 企业付款的商户订单号
     */
    private String partnerTradeNo;
    private String openid;
    /**
     * 是否强制检查用户正式姓名
     */
    private String checkName;
    /**
     * 收款用户真实姓名。
     * 如果check_name设置为FORCE_CHECK，则必填用户真实姓名
     */
    private String reUserName;
    /**
     * 支付金额
     */
    private String amount;
    /**
     * 企业付款备注
     */
    private String desc;
    /**
     * ip地址
     */
    private String spbillCreateIp;


    public TransfersParam() {
        //NO_CHECK：不校验真实姓名
        //FORCE_CHECK：强校验真实姓名
        this.checkName = "FORCE_CHECK";
    }
}
