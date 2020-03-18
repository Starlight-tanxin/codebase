package com.zyb.mini.mall.framework.component;

import com.zyb.mini.mall.pay.user.pay.NotifyField;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.weixin4j.model.pay.PayNotifyResult;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author Created by 谭健 on 2019/11/1. 星期五. 17:08.
 * © All Rights Reserved.
 * <p>
 * 微信支付回调组件
 */

@Slf4j
@Component
public class NotifyComponent {

    /**
     * 获取微信回传给我们的对象
     *
     * @param xmlStr 微信回传过来的xml
     * @return NotifyField
     * @throws Exception .
     */
    public NotifyField getNotifyField(String xmlStr) throws Exception {
        JAXBContext context = JAXBContext.newInstance(PayNotifyResult.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        PayNotifyResult result = (PayNotifyResult) unmarshaller.unmarshal(new StringReader(xmlStr));
        return NotifyField.toNotifyField(result, xmlStr);
    }


    /**
     * 失败
     */
    private static final String FAIL = "FAIL";
    /**
     * 成功
     */
    private static final String SUCCESS = "SUCCESS";


    /**
     * 通知微信回调失败了，让他待会儿发重试请求过来
     *
     * @param writer see HttpServletResponse#getWriter()
     * @param msg    错误的原因
     * @see HttpServletResponse#getWriter()
     */
    public void notifyWxFail(PrintWriter writer, String msg) {
        writer.write(responseSuccessXml(FAIL, msg));
    }

    /**
     * 通知微信回调成功了
     */
    public void notifyWxSuccess(PrintWriter writer) {
        writer.write(responseSuccessXml(SUCCESS, "OK"));
    }


    /**
     * 获取微信回调给我们的xml 字符串
     *
     * @param inputStream request 中的流
     * @return xml 的回调结果
     */
    public String getXmlStr(InputStream inputStream) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            StringBuilder strXml = new StringBuilder();
            String temporaryBuffer;
            while ((temporaryBuffer = reader.readLine()) != null) {
                strXml.append(temporaryBuffer);
            }
            return strXml.toString();
        } catch (Exception e) {
            log.error("代码执行错误 ", e);
        }
        return "";
    }

    /**
     * @return 返回回调成功的消息给微信
     */
    private static String responseSuccessXml(String state, String msg) {
        return "<xml>" + "<return_code><![CDATA[" + state + "]]></return_code>" +
                "<return_msg><![CDATA[" + msg + "]]></return_msg>" +
                "</xml>";
    }
}
