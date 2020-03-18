package com.zyb.mini.mall.pay.pay.user;

import com.alibaba.fastjson.JSON;
import com.zyb.mini.mall.utils.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.weixin4j.pay.WeixinPayException;
import org.weixin4j.pay.model.paywallet.TransPayWallet;
import org.weixin4j.pay.model.paywallet.TransPayWalletResult;
import org.weixin4j.spring.WeixinTemplate;

/**
 * @author Created by 谭健 on 2019/10/30. 星期三. 14:40.
 * © All Rights Reserved.
 * <p>
 * 企业付款到个人
 */
@Slf4j
@Component
public class PayUser {


    private static final String SUCCESS = "SUCCESS";

    private final PayWalletComponmentOverrite payWalletComponment;
    private final WeixinTemplate template;

    public PayUser(PayWalletComponmentOverrite payWalletComponment, WeixinTemplate template) {
        this.payWalletComponment = payWalletComponment;
        this.template = template;
    }

    /**
     * 企业支付给个人
     *
     * @param param 参数
     * @return {@link TransPayWalletResult} 对象
     * @throws WeixinPayException
     */
    public TransPayWalletResult pay(PayUserParam param) throws WeixinPayException {
        TransPayWallet payWallet = new TransPayWallet();
        payWallet.setAmount(param.getAmount());
        payWallet.setOpenid(param.getOpenId());
        payWallet.setDesc(param.getDesc());
        payWallet.setSpbill_create_ip(param.getUserRealIp());
        payWallet.setMch_appid(template.getAppId());
        payWallet.setPartner_trade_no(param.getOrderNumber());
        TransPayWalletResult result = payWalletComponment.transPayWallet(payWallet);
        return result;
//        String code = result.getReturn_code();
//        String resultCode = result.getResult_code();
//        if (SUCCESS.equals(code) && SUCCESS.equals(resultCode)) {
//            log.info("提现成功");
//        }
    }


    // 提现到用户  demo 代码
//    @PostConstruct
    public void p() throws WeixinPayException {
        System.out.println("---------------------------------------");
        PayUserParam param = new PayUserParam();
        // 提现金额最低不低于3毛钱，最高不高于5000块 否则失败
        param.setAmount(30);
        param.setAppId(template.getAppId());
        param.setOrderNumber(UUID.absolutelyUniqueNumber());
        param.setOpenId("oRD4p42LflVD41mnIOZevY7EQats");

        TransPayWalletResult pay = this.pay(param);

        System.out.println(JSON.toJSONString(pay));
    }

}
