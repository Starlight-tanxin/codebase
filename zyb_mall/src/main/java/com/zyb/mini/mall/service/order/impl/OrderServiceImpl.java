package com.zyb.mini.mall.service.order.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Strings;
import com.zyb.mini.mall.constant.*;
import com.zyb.mini.mall.core.Status;
import com.zyb.mini.mall.dao.*;
import com.zyb.mini.mall.exception.ApiException;
import com.zyb.mini.mall.framework.component.ExpressComponent;
import com.zyb.mini.mall.pay.pay.user.PayUser;
import com.zyb.mini.mall.pay.pay.user.PayUserParam;
import com.zyb.mini.mall.pay.user.pay.NotifyField;
import com.zyb.mini.mall.pojo.dto.BeforePayDTO;
import com.zyb.mini.mall.pojo.dto.ExpressDTO;
import com.zyb.mini.mall.pojo.entity.*;
import com.zyb.mini.mall.pojo.param.BasePageParam;
import com.zyb.mini.mall.pojo.param.order.OrderExpressParam;
import com.zyb.mini.mall.pojo.param.order.OrderIndexParam;
import com.zyb.mini.mall.pojo.param.pay.AccountPayParam;
import com.zyb.mini.mall.pojo.param.pay.CheckPayParam;
import com.zyb.mini.mall.pojo.param.pay.PayParam;
import com.zyb.mini.mall.pojo.vo.MngOrderVo;
import com.zyb.mini.mall.service.async.AsyncService;
import com.zyb.mini.mall.service.common.CommonService;
import com.zyb.mini.mall.service.order.OrderService;
import com.zyb.mini.mall.service.trade.PayOrderService;
import com.zyb.mini.mall.utils.CodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.weixin4j.pay.WeixinPayException;
import org.weixin4j.pay.model.paywallet.TransPayWalletResult;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>订单</p>
 *
 * @author tanxin
 * @date 2019/10/30
 */
@Slf4j
@Service
public class OrderServiceImpl extends ServiceImpl<ShopOrderMapper, ShopOrder> implements OrderService {

    @Resource
    private ShopOrderDetailMapper shopOrderDetailMapper;

    @Resource
    private ShopOrderEvaluateMapper shopOrderEvaluateMapper;

    @Resource
    private ShopOrderMapper shopOrderMapper;

    // 鉴赏订单
    @Resource
    private IdentifyMapper identifyMapper;
    // 修复订单
    @Resource
    private MaintainMapper maintainMapper;

    @Autowired
    private ExpressComponent expressComponent;

    @Autowired
    private UserAddressMapper userAddressMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserPunchMapper userPunchMapper;

    @Autowired
    private PayOrderService payOrderService;

    @Autowired
    private PayUser payUser;

    @Autowired
    private AsyncService asyncService;

    @Autowired
    private CommonService commonService;

    @Resource
    private GoodsBookMapper goodsBookMapper;

    /**
     * 运行环境
     */
    @Value("${spring.profiles.active}")
    private String profiles;

    @Override
    public ShopOrder detailOrder(Long orderId) {
        ShopOrder shopOrder = getById(orderId);
        if (shopOrder == null) {
            return null;
        }
        List<ShopOrderDetail> shopOrderDetails = shopOrderDetailMapper.selectDetailByOrderId(orderId);
        shopOrder.setOrderDetails(shopOrderDetails);
        if (!Strings.isNullOrEmpty(shopOrder.getExpressNo()) && !Strings.isNullOrEmpty(shopOrder.getExpressCompanyNo()) && shopOrder.getUserAddressId() != null) {
            UserAddress userAddress = userAddressMapper.getUserAddressById(shopOrder.getUserAddressId());
            ExpressDTO expressDTO = expressComponent.queryExpress(shopOrder.getExpressNo(), shopOrder.getExpressCompanyNo(), userAddress.getMobile());
            shopOrder.setExpressInfo(expressDTO);
        }
        Long userId = shopOrder.getUserId();
        if (shopOrder.getUserAddress() == null) {
            UserAddress userAddress = userAddressMapper.selectUserAddressByUserDefault(userId);
            if (userAddress == null) {
                List<UserAddress> list = userAddressMapper.selectUserAddressByUserId(userId);
                if (list != null && list.size() > 0) {
                    userAddress = list.get(0);
                }
            }
            shopOrder.setUserAddress(userAddress);
        }
        return shopOrder;
    }

    @Override
    public ShopOrderEvaluate addEvaluate(ShopOrderEvaluate shopOrderEvaluate) {
        shopOrderEvaluate.setCreatedTime(LocalDateTime.now());
        synchronized (this) {
            int isAdd = shopOrderEvaluateMapper.insert(shopOrderEvaluate);
            if (isAdd > 0) {
                ShopOrder shopOrder = shopOrderMapper.selectById(shopOrderEvaluate.getShopOrderId());
                if (shopOrder != null && shopOrder.getOrderState().equals(ShopOrderState.NOT_EVA)) {
                    shopOrder.setOrderState(ShopOrderState.EVA);
                    // 完成时间
                    shopOrder.setCompleteTime(LocalDateTime.now());
                    shopOrderMapper.updateById(shopOrder);
                } else {
                    throw new ApiException(Status.ORDER_NO_EXIST, "订单不存在或者不是待评价状态");
                }
            }
        }
        return shopOrderEvaluate;
    }

    @Override
    public IPage<ShopOrderEvaluate> selectPageEvaluate(BasePageParam pageParam) {
        Page<ShopOrderEvaluate> queryPage = new Page<>(pageParam.getPage(), pageParam.getPageSize());
        queryPage.setDesc("created_time");
        IPage<ShopOrderEvaluate> page = shopOrderEvaluateMapper.selectPage(queryPage, null);
        return page;
    }

    @Override
    public ShopOrder sendGoods(OrderExpressParam param) {
        ShopOrder shopOrder = getById(param.getOrderId());
        if (shopOrder == null || !shopOrder.getOrderState().equals(ShopOrderState.NOT_SEND_GOODS)) {
            throw new ApiException(Status.ORDER_NO_EXIST, "订单不存在或不是待发货状态");
        }
        shopOrder.setExpressCompanyNo(param.getExpressCompanyNo());
        shopOrder.setExpressNo(param.getExpressNo());
        // 发货时间
        shopOrder.setSendGoodsTime(LocalDateTime.now());
        shopOrderMapper.updateById(shopOrder);
        return shopOrder;
    }

    /**
     * 增加商品库存
     *
     * @param orderId 商品id
     */
    private synchronized void updateGoodsNumAdd(Long orderId) {
        List<ShopOrderDetail> detailList = shopOrderDetailMapper.selectDetailByOrderId(orderId);
        detailList.forEach(detail -> {
            goodsBookMapper.updateGoodsNumAdd(detail.getGoodsBookId(), detail.getNum());
        });
    }

    @Override
    public ShopOrder cancelOrder(Long orderId) {
        ShopOrder shopOrder = getById(orderId);
        if (shopOrder == null || !shopOrder.getOrderState().equals(ShopOrderState.NOT_PAY)) {
            throw new ApiException(Status.ORDER_NO_EXIST, "订单不存在或不是待支付状态");
        }
        shopOrder.setOrderState(ShopOrderState.CANCEL_ORDER);
        int rows = shopOrderMapper.updateById(shopOrder);
        if (rows > 0) {
            // 增加库存
            updateGoodsNumAdd(shopOrder.getId());
        }
        return shopOrder;
    }

    @Override
    public ShopOrder applyRefund(Long orderId) {
        ShopOrder shopOrder = getById(orderId);
        if (shopOrder == null || !(shopOrder.getOrderState().equals(ShopOrderState.NOT_SEND_GOODS) || shopOrder.getOrderState().equals(ShopOrderState.NOT_PULL_GOODS))) {
            throw new ApiException(Status.ORDER_NO_EXIST, "订单不存在或不是可以发起退款的状态");
        }
        shopOrder.setOrderState(ShopOrderState.APPLY_REFUND);
        shopOrderMapper.updateById(shopOrder);
        return shopOrder;
    }

    /**
     * 生产退款的退款单
     *
     * @param payOrder 原支付单
     * @return
     */
    private PayOrder refundPayOrder(PayOrder payOrder, boolean isAccountPay) {
        BigDecimal payAmount = payOrder.getAmont();
        BigDecimal refundAmount = payAmount.multiply(CommonConstant.REFUND_PROPORTION);
        String payOrderNo = LocalDateTime.now().format(DateFormatter.DATE_TIME_ORDER) + CodeUtils.createRandomCharData(16);
        PayOrder refund = new PayOrder()
                .setPayOrderNo(payOrderNo)
                .setState(PayOrderState.PAYING)
                .setIsIncome(true)
                .setIsAccountPay(isAccountPay)
                .setRefundPayOrderNo(payOrder.getPayOrderNo())
                .setUserId(payOrder.getUserId())
                .setOtherId(0L)
                .setPayType(PayOrderType.REFUND_PAY)
                .setAmont(refundAmount)
                .setRealAmont(refundAmount)
                .setPayRequestStr(CommonConstant.REFUND_MAKE)
                .setCreatedTime(LocalDateTime.now());
        payOrderService.save(refund);
        return refund;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ShopOrder refund(Long orderId) {
        ShopOrder shopOrder = getById(orderId);
        if (shopOrder == null || !shopOrder.getOrderState().equals(ShopOrderState.APPLY_REFUND)) {
            throw new ApiException(Status.ORDER_NO_EXIST, "订单不存在或不是可以发起退款的状态");
        }
        synchronized (this) {
            LambdaQueryWrapper<PayOrder> poQw = new QueryWrapper<PayOrder>().lambda()
                    .eq(PayOrder::getOtherId, shopOrder.getId())
                    .eq(PayOrder::getPayType, PayOrderType.SHOP_PAY)
                    .last(" LIMIT 1");
            PayOrder payOrder = payOrderService.getOne(poQw);
            if (payOrder == null) {
                throw new ApiException(Status.DATA_NO_EXIST, "找不到对应的支付数据");
            }
            User user = userMapper.selectById(shopOrder.getUserId());
            if (payOrder.getIsAccountPay()) {
                // 是账户支付
                PayOrder refund = refundPayOrder(payOrder, true);
                BigDecimal amount = user.getActualAmount().add(refund.getAmont());
                user.setActualAmount(amount);
                int rows = userMapper.updateById(user);
                if (rows > 0) {
                    refund.setState(PayOrderState.PAY_FINAL);
                    refund.setPayCompleteTime(LocalDateTime.now());
                    payOrderService.updateById(refund);
                    // TODO 发送短信通知
                    asyncService.sendRefund(refund.getUserId(), refund.getAmont());
                } else {
                    throw new ApiException(Status.SYS_ERROR, "退款失败！账户金额退款！可能是更新用户信息失败");
                }
            } else {
                // 不是账户支付
                PayOrder refund = refundPayOrder(payOrder, false);
                PayUserParam param = new PayUserParam();
                int refundAmount = refund.getAmont().multiply(CommonConstant.FEN_2_YUAN).intValue();
                if (CommonConstant.PROD.equals(profiles)) {
                    param.setAmount(refundAmount);
                } else {
                    // 最低提现金额
                    param.setAmount(30);
                }
                param.setOrderNumber(refund.getPayOrderNo());
                param.setOpenId(user.getWxOpenId());
                param.setDesc("退款");
                try {
                    TransPayWalletResult result = payUser.pay(param);
                    if (CommonConstant.SUCCESS.equalsIgnoreCase(result.getResult_code()) && CommonConstant.SUCCESS.equalsIgnoreCase(result.getReturn_code())) {
                        //TODO 业务成功
                        refund.setState(PayOrderState.PAY_FINAL);
                        refund.setPayCompleteTime(LocalDateTime.now());
                        refund.setPayCallbackStr(JSON.toJSONString(result));
                        refund.setThirdOrderNo(result.getPayment_no());
                        payOrderService.updateById(payOrder);
                        // TODO 发送短信通知
                        asyncService.sendRefund(refund.getUserId(), refund.getAmont());
                    } else {
                        throw new ApiException(Status.SYS_ERROR, "退款失败！错误未知");
                    }
                } catch (WeixinPayException e) {
                    log.error("微信退款出现错误 错误 ： {} ; \t  错误描述 ： {}", e, e.getMessage());
                    throw new ApiException(Status.SYS_ERROR, "退款失败！错误未知");
                }
            }
            shopOrder.setOrderState(ShopOrderState.REFUND);
            int rows = shopOrderMapper.updateById(shopOrder);
            if (rows > 0) {
                updateGoodsNumAdd(shopOrder.getId());
            }

        }
        return shopOrder;
    }

    /**
     * 计算商城订单价格
     *
     * @param userId  用户订单
     * @param orderId 商城订单id
     * @return 金额 单位元
     */
    public BigDecimal computeShopOrderPrice(Long userId, Long orderId) {
        LambdaQueryWrapper<ShopOrder> qw = new QueryWrapper<ShopOrder>().lambda().eq(ShopOrder::getId, orderId).eq(ShopOrder::getUserId, userId);
        ShopOrder shopOrder = getOne(qw);
        if (shopOrder == null || shopOrder.getOrderState() == null) {
            throw new ApiException(Status.ORDER_NO_EXIST);
        }
        if (!shopOrder.getOrderState().equals(1)) {
            throw new ApiException(Status.ORDER_ERROR_STATE);
        }
        BigDecimal price;
        if (CommonConstant.PROD.equals(profiles)) {
            // 正式环境
            price = shopOrder.getOrderPrice().add(shopOrder.getExpressPrice());
        } else {
            price = BigDecimal.valueOf(0.01);
        }
        return price;
    }

    /**
     * 计算鉴赏价格
     *
     * @param userId  用户订单
     * @param orderId 商城订单id
     * @return 金额 单位元
     */
    private BigDecimal computeIdentifyPrice(Long userId, Long orderId) {
        LambdaQueryWrapper<Identify> qw = new QueryWrapper<Identify>().lambda().eq(Identify::getId, orderId).eq(Identify::getUserId, userId);
        Identify identify = identifyMapper.selectOne(qw);
        if (identify == null || identify.getIdentify() == null) {
            throw new ApiException(Status.ORDER_NO_EXIST);
        }
        if (!identify.getIdentify().equals(IdentifyState.CREATED)) {
            throw new ApiException(Status.ORDER_ERROR_STATE);
        }
        BigDecimal price;
        if (CommonConstant.PROD.equals(profiles)) {
            // 正式环境
            price = identify.getAmount();
        } else {
            price = BigDecimal.valueOf(0.01);
        }
        return price;
    }

    /**
     * 计算修复价格（首款或者尾款）
     *
     * @param userId  用户订单
     * @param orderId 商城订单id
     * @return 金额 单位元
     */
    private BigDecimal computeMaintainPrice(Long userId, Long orderId, Integer repairType) {
        if (repairType == null) {
            throw new ApiException(Status.ORDER_ERROR_STATE, "不确认是首款还是尾款");
        }
        LambdaQueryWrapper<Maintain> qw = new QueryWrapper<Maintain>().lambda().eq(Maintain::getId, orderId).eq(Maintain::getUserId, userId);
        Maintain maintain = maintainMapper.selectOne(qw);
        if (maintain == null || maintain.getMaintainState() == null) {
            throw new ApiException(Status.ORDER_NO_EXIST);
        }
        if (!maintain.getMaintainState().equals(MaintainState.CM_EVALUATE) && repairType.equals(0)) {
            throw new ApiException(Status.ORDER_ERROR_STATE);
        }
        if (!maintain.getMaintainState().equals(MaintainState.COMPLETE) && repairType.equals(1)) {
            throw new ApiException(Status.ORDER_ERROR_STATE);
        }
        BigDecimal price;
        if (CommonConstant.PROD.equals(profiles)) {
            // 正式环境
            price = maintain.getCmMaintainAmount().divide(BigDecimal.valueOf(2), 2, BigDecimal.ROUND_HALF_UP);
        } else {
            price = BigDecimal.valueOf(0.01);
        }
        return price;
    }

    /**
     * 计算充值、提现、助力打卡 的金额
     *
     * @param payParam 支付参数
     * @return 金额 单位元
     */
    private BigDecimal computePrice(PayParam payParam) {
        if (payParam.getAmount() == null) {
            throw new ApiException(Status.PARAM_LOST);
        }
        if (CommonConstant.PROD.equals(profiles)) {
            // 正式环境
            return payParam.getAmount();
        } else {
            return BigDecimal.valueOf(0.01);
        }
    }


    @Override
    public BeforePayDTO getBeforePayDTO(Long userId, PayParam payParam) {
        Long orderId = payParam.getOrderId();
        // 是否尾款 0-首款   1-尾款
        Integer repairType = payParam.getRepairType();
        BeforePayDTO dto = new BeforePayDTO()
                .setOtherId(payParam.getOrderId())
                .setRepairType(repairType)
                .setUserId(userId);
        BigDecimal amount;
        switch (payParam.getOrderType()) {
            case PayOrderType.JS_PAY: // 1 - 鉴赏支付单
                amount = computeIdentifyPrice(userId, orderId);
                dto.setIsIncome(false);
                break;
            case PayOrderType.XF_PAY: // 2 - 修复得支付单（修复是2次支付）
                amount = computeMaintainPrice(userId, orderId, repairType);
                dto.setIsIncome(false);
                break;
            case PayOrderType.SHOP_PAY: // 3 - 书城得支付单
                if (payParam.getUserAddressId() == null || payParam.getUserAddressId().equals(0L)) {
                    throw new ApiException(Status.PARAM_LOST);
                }
                amount = computeShopOrderPrice(userId, orderId);
                dto.setIsIncome(false);
                // 更新收货地址
                ShopOrder order = new ShopOrder().setId(orderId).setUserAddressId(payParam.getUserAddressId());
                shopOrderMapper.updateById(order);
                break;
            case PayOrderType.ZL_PAY: // 4 - 助力打卡得支付单
                amount = computePrice(payParam);
                dto.setIsIncome(false);
                break;
            case PayOrderType.CZ_PAY: // 5 - 充值得支付单
                amount = computePrice(payParam);
                dto.setIsIncome(false);
                break;
            default:
                throw new ApiException(Status.ILLEGAL_PARAM);
        }
        dto.setPayType(payParam.getOrderType());
        dto.setAmount(amount);
        return dto;
    }


    /**
     * 各种判断成功后更新支付订单
     *
     * @param payOrder      支付单
     * @param xmlStr        微信原始xml
     * @param transactionId 微信内部订单号
     */
    private void updateSuccessPayOrder(PayOrder payOrder, String xmlStr, String transactionId) {
        LocalDateTime now = LocalDateTime.now();
        payOrder.setUpdatedTime(now);
        payOrder.setPayCompleteTime(now);
        payOrder.setPayCallbackStr(xmlStr);
        payOrder.setState(PayOrderState.PAY_FINAL);
        payOrder.setThirdOrderNo(transactionId);
        payOrderService.updateById(payOrder);
    }

    /**
     * 回调处理商城订单
     *
     * @param payOrder      支付单
     * @param xmlStr        微信原字符串
     * @param transactionId 微信内部订单号
     * @return 是否处理成功
     */
    private boolean callBackShopOrder(PayOrder payOrder, String xmlStr, String transactionId) {
        Long orderId = payOrder.getOtherId();
        ShopOrder shopOrder = getById(orderId);
        if (shopOrder == null) {
            log.error("callBackShopOrder : 商城订单 \t 出现错误 : 订单为空 \t 微信数据 : {}", xmlStr);
            return false;
        }
        if (!shopOrder.getOrderState().equals(ShopOrderState.NOT_PAY)) {
            log.error("callBackShopOrder : 商城订单 \t 出现错误 : 本订单不是待支付状态 \t 微信数据 : {}", xmlStr);
            return false;
        }
        // 支付时间
        shopOrder.setPayTime(LocalDateTime.now());
        shopOrder.setOrderState(ShopOrderState.NOT_SEND_GOODS);
        shopOrderMapper.updateById(shopOrder);
        updateSuccessPayOrder(payOrder, xmlStr, transactionId);
        // TODO 需要发送短信
        asyncService.sendOrderSms(shopOrder.getOrderNo(), shopOrder.getUserAddressId());
        return true;
    }

    /**
     * 回调处理鉴赏单
     *
     * @param payOrder      支付单
     * @param xmlStr        微信原字符串
     * @param transactionId 微信订单号
     * @return 是否处理成功
     */
    private boolean callbackIdentify(PayOrder payOrder, String xmlStr, String transactionId) {
        Long otherId = payOrder.getOtherId();
        Identify identify = identifyMapper.selectById(otherId);
        if (identify == null) {
            log.error("callbackIdentify : 鉴赏订单 \t 出现错误 : 订单为空 \t 微信数据 : {}", xmlStr);
            return false;
        }
        if (!identify.getIdentify().equals(IdentifyState.CREATED)) {
            log.error("callbackIdentify : 鉴赏订单 \t 出现错误 : 本鉴赏单不是待支付状态 \t 微信数据 : {}", xmlStr);
            return false;
        }
        identify.setIdentify(IdentifyState.COMMIT);
        identifyMapper.updateById(identify);
        updateSuccessPayOrder(payOrder, xmlStr, transactionId);
        //TODO 可能需要增加发送短信
        asyncService.sendIdentify(identify.getUserProId());
        return true;
    }

    /**
     * 回调处理修复单
     *
     * @param payOrder      支付单
     * @param xmlStr        微信原字符串
     * @param transactionId 微信内部订单号
     * @return 是否处理成功
     */
    private boolean callbackMaintain(PayOrder payOrder, String xmlStr, String transactionId) {
        Long otherId = payOrder.getOtherId();
        Maintain maintain = maintainMapper.selectById(otherId);
        if (maintain == null) {
            log.error("callbackMaintain : 修复订单 \t 出现错误 : 订单为空 \t 微信数据 : {}", xmlStr);
            return false;
        }
        // 0-首款 1-尾款
        Integer repairType = payOrder.getRepairType();
        boolean isSuccess = false;
        if (repairType.equals(0) && maintain.getMaintainState().equals(MaintainState.CM_EVALUATE)) {
            maintain.setMaintainState(MaintainState.FIRST_PAYING);
            isSuccess = true;
        }
        if (repairType.equals(1) && maintain.getMaintainState().equals(MaintainState.COMPLETE)) {
            maintain.setMaintainState(MaintainState.TAIL_PAYING);
            isSuccess = true;
        }
        if (isSuccess) {
            maintainMapper.updateById(maintain);
            updateSuccessPayOrder(payOrder, xmlStr, transactionId);
            return true;
        }
        log.error("callbackMaintain : 修复订单 \t 出现错误 : 本鉴修复单不是待支付状态 \t 微信数据 : {}", xmlStr);
        return false;
    }

    /**
     * 回调处理助力打卡
     *
     * @param payOrder      支付单
     * @param xmlStr        微信原字符串
     * @param transactionId 微信内部订单号
     * @return 是否处理成功
     */
    private boolean callbackPunch(PayOrder payOrder, String xmlStr, String transactionId) {
        Long userId = payOrder.getUserId();
        User user = userMapper.selectById(userId);
        if (user == null) {
            log.error("callbackPunch : 助力打卡 \t 出现错误 : 用户为空 \t 微信数据 : {}", xmlStr);
            return false;
        }
        LambdaQueryWrapper<UserPunch> upQw = new QueryWrapper<UserPunch>().lambda().eq(UserPunch::getUserId, userId).orderByDesc(UserPunch::getId).last(" LIMIT 1 ");
        UserPunch lastPunch = userPunchMapper.selectOne(upQw);
        if (lastPunch == null) {
            log.error("callbackPunch : 助力打卡 \t 出现错误 : 找不到任何一次打卡信息 \t 微信数据 : {}", xmlStr);
            return false;
        }
        int count = payOrder.getAmont().divide(CommonConstant.MONEY_COUNT_PUNCH, 2, BigDecimal.ROUND_HALF_UP).intValue();
        UserPunch userPunch = new UserPunch()
                .setUserId(userId)
                .setPunchDate(LocalDateTime.now())
                .setPunchCount((long) count)
                .setSurnameId(lastPunch.getSurnameId());
        userPunchMapper.insert(userPunch);
        updateSuccessPayOrder(payOrder, xmlStr, transactionId);
        //TODO 可能需要增加发送短信
        return true;
    }


    /**
     * 充值处理助力打卡
     *
     * @param payOrder      支付单
     * @param xmlStr        微信原字符串
     * @param transactionId 微信内部订单号
     * @return 是否处理成功
     */
    private boolean callbackRecharge(PayOrder payOrder, String xmlStr, String transactionId) {
        Long userId = payOrder.getUserId();
        User user = userMapper.selectById(userId);
        if (user == null) {
            log.error("callbackRecharge : 账户充值 \t 出现错误 : 用户为空 \t 微信数据 : {}", xmlStr);
            return false;
        }
        BigDecimal amount = payOrder.getAmont();
        userMapper.updateAmountById(userId, amount);
        updateSuccessPayOrder(payOrder, xmlStr, transactionId);
        // 更新用户缓存
        commonService.updateUserCache(userId);
        //TODO 可能需要发送短信
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean callbackProcess(NotifyField notifyField, String xmlStr) {
        String payOrderNo = notifyField.getOutTradeNo();
        LambdaQueryWrapper<PayOrder> payQw = new QueryWrapper<PayOrder>().lambda().eq(PayOrder::getPayOrderNo, payOrderNo);
        PayOrder payOrder = payOrderService.getOne(payQw);
        if (payOrder == null) {
            return false;
        }
        Integer payType = payOrder.getPayType();
        switch (payType) {
            case PayOrderType.JS_PAY: // 1 - 鉴赏支付单
                return callbackIdentify(payOrder, xmlStr, notifyField.getTransactionId());
            case PayOrderType.XF_PAY: // 2 - 修复得支付单（修复是2次支付）
                return callbackMaintain(payOrder, xmlStr, notifyField.getTransactionId());
            case PayOrderType.SHOP_PAY: // 3 - 书城得支付单
                return callBackShopOrder(payOrder, xmlStr, notifyField.getTransactionId());
            case PayOrderType.ZL_PAY: // 4 - 助力打卡得支付单
                return callbackPunch(payOrder, xmlStr, notifyField.getTransactionId());
            case PayOrderType.CZ_PAY: // 5 - 充值得支付单
                return callbackRecharge(payOrder, xmlStr, notifyField.getTransactionId());
            default:
                log.error("不被支付回调处理的类型 ------> 支付单数据 : {}", JSONObject.toJSONString(payOrder));
                return false;
        }
    }

    private boolean callbackProcessByAccountPay(PayOrder payOrder) {
        Integer payType = payOrder.getPayType();
        switch (payType) {
            case PayOrderType.JS_PAY: // 1 - 鉴赏支付单
                return callbackIdentify(payOrder, CommonConstant.ACCOUNT_PAY_MARK, null);
            case PayOrderType.XF_PAY: // 2 - 修复得支付单（修复是2次支付）
                return callbackMaintain(payOrder, CommonConstant.ACCOUNT_PAY_MARK, null);
            case PayOrderType.SHOP_PAY: // 3 - 书城得支付单
                return callBackShopOrder(payOrder, CommonConstant.ACCOUNT_PAY_MARK, null);
            case PayOrderType.ZL_PAY: // 4 - 助力打卡得支付单
                return callbackPunch(payOrder, CommonConstant.ACCOUNT_PAY_MARK, null);
            default:
                log.error("不被支付回调处理的类型 ------> 支付单数据 : {}", JSONObject.toJSONString(payOrder));
                return false;
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void accountPay(Long userId, AccountPayParam param) {
        BeforePayDTO dto = getBeforePayDTO(userId, param);
        synchronized (this) {
            User user = userMapper.selectById(userId);
            BigDecimal amount = dto.getAmount();
            if (user.getActualAmount().doubleValue() >= amount.doubleValue()) {
                if (dto.getPayType().equals(PayOrderType.CZ_PAY)) {
                    // TODO 不能使用账户金额 对账户金额进行充值
                    throw new ApiException(Status.SYS_ERROR, " 不能使用账户金额对账户金额进行充值");
                }
                PayOrder payOrder = payOrderService.createPayOrderByAccountPay(dto);
                if (payOrder == null) {
                    //TODO 抛出异常
                    throw new ApiException(Status.PAY_ORDER_CREATE_FAIL);
                }
                BigDecimal nowActualAmount = user.getActualAmount().subtract(amount);
                user.setActualAmount(nowActualAmount);
                userMapper.updateById(user);
                // 处理支付成功的数据
                callbackProcessByAccountPay(payOrder);
            } else {
                // TODO 账户余额不足
                throw new ApiException(Status.ACCOUNT_AMOUNT_NOT);
            }
        }
    }


    @Override
    public Boolean checkPaySuccess(CheckPayParam param) {
        Integer payType = param.getOrderType();
        switch (payType) {
            case PayOrderType.JS_PAY: // 1 - 鉴赏支付单
                Identify identify = identifyMapper.selectById(param.getOrderId());
                if (identify != null && identify.getIdentify().equals(IdentifyState.COMMIT)) {
                    return true;
                }
            case PayOrderType.XF_PAY: // 2 - 修复得支付单（修复是2次支付）
                Maintain maintain = maintainMapper.selectById(param.getOrderId());
                if (maintain != null && (maintain.getMaintainState().equals(MaintainState.FIRST_PAYING) || maintain.getMaintainState().equals(MaintainState.TAIL_PAYING))) {
                    return true;
                }
            case PayOrderType.SHOP_PAY: // 3 - 书城得支付单
                ShopOrder shopOrder = shopOrderMapper.selectById(param.getOrderId());
                if (shopOrder != null && shopOrder.getOrderState().equals(ShopOrderState.NOT_SEND_GOODS)) {
                    return true;
                }
            default:
                return false;
        }
    }

    @Override
    public Page<ShopOrder> selectPageByParam(OrderIndexParam param) {
        Page<ShopOrder> page = new Page<>(param.getPage(), param.getPageSize());
        page.setDesc("cretaed_time");
        List<ShopOrder> list = shopOrderMapper.selectPageByParam(page, param);
        page.setRecords(list);
        return page;
    }

    @Override
    public List<MngOrderVo> listByState(int orderState) {
        // TODO 查询订单列表
        return null;
    }


}
