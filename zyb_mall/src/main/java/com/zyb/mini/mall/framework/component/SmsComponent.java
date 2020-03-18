package com.zyb.mini.mall.framework.component;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.zyb.mini.mall.config.AliMsgConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>阿里短信接入</p>
 *
 * @author: Tx
 * @date: 2019/11/3
 */
@Slf4j
@Component
public class SmsComponent {

    /**
     * 发送消息
     *
     * @param mobile       手机号码
     * @param code         验证吗
     * @param templateCode 短信消息模板
     */
    private void sendMsg(String mobile, String code, String templateCode) {
        DefaultProfile profile = DefaultProfile.getProfile(AliMsgConfig.REGION_ID, AliMsgConfig.ACCESS_KEY_ID, AliMsgConfig.ACCESS_KEY_SECRET);
        IAcsClient client = new DefaultAcsClient(profile);
        // 发送的短信内容
        Map<String, String> param = new HashMap<>();
        param.put("code", code);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain(AliMsgConfig.DOMAIN);
        request.setVersion(AliMsgConfig.VERSIO);
        request.setAction(AliMsgConfig.API_SEND_SMS);
        request.putQueryParameter("RegionId", AliMsgConfig.REGION_ID);
        request.putQueryParameter("PhoneNumbers", mobile);
        request.putQueryParameter("SignName", AliMsgConfig.SIGN_NAME);
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", JSON.toJSONString(param));
        try {
            CommonResponse response = client.getCommonResponse(request);
            log.info("阿里短信发送返回内容 -----> {}", response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送下单成功得消息
     *
     * @param mobile 电话号码
     * @param code   发送得内容
     */
    public void sendMsgByOrder(String mobile, String code) {
        sendMsg(mobile, code, AliMsgConfig.ORDER_TEMPLATE);
    }


    /**
     * 发送退款成功得消息
     *
     * @param mobile 电话号码
     * @param code   发送得内容
     */
    public void sendMsgByRefund(String mobile, String code) {
        sendMsg(mobile, code, AliMsgConfig.REFUND_TEMPLATE);
    }

    /**
     * 发送修复订单得回复消息
     *
     * @param mobile 电话号码
     * @param code   发送得内容
     */
    public void sendMsgByMaintain(String mobile, String code) {
        sendMsg(mobile, code, AliMsgConfig.MAINTAIN_TEMPLATE);
    }


    /**
     * 发送鉴赏的通知
     *
     * @param mobile 电话号码
     * @param code   发送得内容
     */
    public void sendMsgByIdentify(String mobile, String code) {
        sendMsg(mobile, code, AliMsgConfig.IDENTIFY_TEMPLATE);
    }


    /**
     * 身份验证吗
     *
     * @param mobile 电话号码
     * @param code   发送得内容
     */
    public void sendMsgByCode(String mobile, String code) {
        sendMsg(mobile, code, AliMsgConfig.CODE_TEMPLATE);
    }
}
