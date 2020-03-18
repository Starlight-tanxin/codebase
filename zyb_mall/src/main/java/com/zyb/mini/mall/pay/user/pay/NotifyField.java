package com.zyb.mini.mall.pay.user.pay;

import lombok.Data;
import org.weixin4j.model.pay.PayNotifyResult;

/**
 * @author Created by 谭健 on 2019/11/1. 星期五. 11:42.
 * © All Rights Reserved.
 */
@Data
public class NotifyField {

    /**
     * 微信单号
     */
    private String transactionId;
    /**
     * 银行类型
     */
    private String bankType;
    private String openid;
    /**
     * 内部订单号
     */
    private String outTradeNo;
    /**
     * 总金额，分
     */
    private String totalFee;
    /**
     * 回传用户字段，为空，可不用
     */
    private String attach;
    /**
     * 微信回传的xml 源串
     */
    private String notifyOriginal;


    public static NotifyField toNotifyField(PayNotifyResult result, String strXml) {
        NotifyField field = new NotifyField();

        field.setAttach(result.getAttach());
        field.setBankType(result.getBank_type());
        field.setOpenid(result.getOpenid());
        field.setOutTradeNo(result.getOut_trade_no());
        field.setTotalFee(result.getTotal_fee());
        field.setNotifyOriginal(strXml);

        return field;
    }

}
