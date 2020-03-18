package com.zyb.mini.mall.service.async;

import com.zyb.mini.mall.dao.UserAddressMapper;
import com.zyb.mini.mall.dao.UserMapper;
import com.zyb.mini.mall.dao.UserProMapper;
import com.zyb.mini.mall.framework.component.SmsComponent;
import com.zyb.mini.mall.pojo.entity.User;
import com.zyb.mini.mall.pojo.entity.UserAddress;
import com.zyb.mini.mall.pojo.entity.UserPro;
import com.zyb.mini.mall.utils.ThreadPoolUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * <p>异步操作</p>
 *
 * @author: Tx
 * @date: 2019/11/14
 */
@Slf4j
@Component
public class AsyncService {

    @Autowired
    private SmsComponent smsComponent;
    @Resource
    private UserAddressMapper userAddressMapper;
    @Resource
    private UserProMapper userProMapper;
    @Resource
    private UserMapper userMapper;


    /**
     * 发送短信
     *
     * @param shopOrderNo   订单号
     * @param userAddressId 收获地址
     */
    public void sendOrderSms(String shopOrderNo, Long userAddressId) {
        try {
            ThreadPoolUtils.getsInstance().execute(() -> {
                UserAddress userAddress = userAddressMapper.selectById(userAddressId);
                if (userAddress == null) {
                    log.error("发送短信失败 ---- 种类 ： 商城订单通知  \t 原因 ：收货地址为空");
                    return;
                }
                smsComponent.sendMsgByOrder(userAddress.getMobile(), shopOrderNo);
            });
        } catch (Exception e) {
            e.printStackTrace();
            log.error("发送短信失败 ---- 种类 ： 商城订单通知 \t 未知错误 , 错误 ： {}", e.getMessage());
        }
    }

    /**
     * 发送鉴赏通知
     *
     * @param userProId
     */
    public void sendIdentify(Long userProId) {
        try {
            ThreadPoolUtils.getsInstance().execute(() -> {
                UserPro userPro = userProMapper.selectById(userProId);
                if (userPro == null) {
                    log.error("发送短信失败 ---- 种类 ： 鉴赏单通知  \t 原因 ：鉴赏专家为空");
                    return;
                }
                smsComponent.sendMsgByIdentify(userPro.getMobile(), userPro.getRealname());
            });
        } catch (Exception e) {
            e.printStackTrace();
            log.error("发送短信失败 ---- 种类 ： 鉴赏单通知 \t 未知错误 , 错误 ： {}", e.getMessage());
        }
    }

    /**
     * 退款通知
     *
     * @param userId
     * @param amount
     */
    public void sendRefund(Long userId, BigDecimal amount) {
        try {
            ThreadPoolUtils.getsInstance().execute(() -> {
                User user = userMapper.selectById(userId);
                if (user == null) {
                    log.error("发送短信失败 ---- 种类 ： 退款单通知  \t 原因 ：用户为空");
                    return;
                }
                double doubleValue = amount.divide(BigDecimal.valueOf(1), 2, BigDecimal.ROUND_HALF_UP).doubleValue();
                smsComponent.sendMsgByRefund(user.getMobile(), String.valueOf(doubleValue));
            });
        } catch (Exception e) {
            e.printStackTrace();
            log.error("发送短信失败 ---- 种类 ： 退款通知 \t 未知错误 , 错误 ： {}", e.getMessage());
        }
    }

    /**
     * 发送修复单通知
     *
     * @param userId  用户id
     * @param orderNo 修复单号
     */
    public void sendMaintain(Long userId, String orderNo) {
        try {
            ThreadPoolUtils.getsInstance().execute(() -> {
                User user = userMapper.selectById(userId);
                if (user == null) {
                    log.error("发送短信失败 ---- 种类 ： 退款单通知  \t 原因 ：用户为空");
                    return;
                }
                smsComponent.sendMsgByMaintain(user.getMobile(), orderNo);
            });
        } catch (Exception e) {
            e.printStackTrace();
            log.error("发送短信失败 ---- 种类 ： 修复单通知 \t 未知错误 , 错误 ： {}", e.getMessage());
        }
    }
}
