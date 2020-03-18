package com.zyb.mini.mall.core;

/**
 * 统一返回code
 *
 * @author tanxin
 * @date 2019/5/16
 */
public enum Status {
    /**
     * 默认成功
     */
    SUCCESS(200, "OK"),
    /**
     * 拒绝接收数据
     */
    REFUSE_ACCEPT(270, "拒绝接收本次数据"),
    /**
     * 普通错误
     */
    REQUEST_BAD(400, "普通错误"),
    /**
     * 参数丢失
     */
    PARAM_LOST(401, "参数丢失"),
    /**
     * 没有授权
     */
    UNAUTHORIZED(402, "没有授权,请前往个人中心授权登录"),
    /**
     * 没有权限访问
     */
    FORBIDDEN(403, "没有权限访问"),
    /**
     * 数据不存在
     */
    DATA_NO_EXIST(420, "数据不存在"),
    /**
     * 数据一存在
     */
    DATA_EXIST(421, "数据已存在"),
    /**
     * 数据添加失败
     */
    DATA_ADD_FAIL(422, "数据添加失败"),
    /**
     * 非法参数
     */
    ILLEGAL_PARAM(423, "非法参数"),
    /**
     * 系统异常
     */
    SYS_ERROR(500, "系统异常"),
    /**
     * token错误
     */
    TOKEN_ERROR(510, "TOKEN错误"),
    /**
     * 签名错误
     */
    SIGN_ERROR(511, "签名错误"),
    /**
     * TOKEN为空
     */
    TOKEN_EMPTY(512, "TOKEN为空"),
    /**
     * 签名为空
     */
    SIGN_EMPTY(513, "签名为空"),
    /**
     * 必填参数为空
     */
    MAIN_PARAM_EMPTY(514, "必填参数为空"),
    /**
     * 请求的JSON格式错误
     */
    REQUEST_FORMAT_ERROR(515, "请求的JSON格式错误"),

    /**
     * 请求的JSON格式错误
     */
    REQUEST_HEAD_ERROR(516, "HTTP请求头错误"),


    /**
     * 释放锁出错
     */
    LOCK_ERROR(701, "释放锁出错"),

    /**
     * excel解析出错
     */
    EXCEL_ANALYSIS_ERROR(801, "excel解析出错"),

    /**
     * 唯一标识重复
     */
    NO_UNIQUE(520, "唯一标识重复"),
    /**
     * 基础信息不全
     */
    TD_BASE_ERROR(521, "该数据基础信息不全"),
    /**
     * 订单不存在
     */
    ORDER_NO_EXIST(523, "订单不存在"),

    /**
     * 商品库存不足
     */
    GOODS_NO_NUM(524, "商品库存不足"),

    /**
     * 专家不存在
     */
    PRO_NOT_EXIST(526, "专家不存在"),

    /**
     * 订单状态错误
     */
    ORDER_ERROR_STATE(525, "订单状态错误，无法操作"),
    /**
     * 本状态下无法进行评价
     */
    EVA_NOT(555, "本状态下无法进行评价"),
    /**
     * 还没设置支付密码
     */
    NOT_SET_PWD(2010, "还没设置支付密码"),

    /**
     * 没有设置手机号
     */
    MOBILE_NOT_SET(2011, "没有设置手机号"),

    /**
     * CODE错误
     */
    CODE_ERROR(2012, "CODE错误"),

    /**
     * 输入的支付密码错误
     */
    INPUT_PWD_ERROR(2012, "输入的支付密码错误"),

    /**
     * 账户金额不够
     */
    ACCOUNT_AMOUNT_NOT(3011, "账户金额不够"),

    /**
     * 支付单创建失败
     */
    PAY_ORDER_CREATE_FAIL(3012, "支付单创建失败"),

    /**
     * 提现失败！金额不足
     */
    CASH_ERROR_AMOUNT(4010, "提现失败！金额不足"),
    /**
     * 不是专家用户
     */
    NOT_PRO_USER(5010, "不是专家用户"),
    /**
     * 专家和订单不匹配
     */
    IDENTIFY_NOT_PRO(5011, "专家和订单不匹配");


    private int status;

    private String message;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    Status(int status, String message) {
        this.status = status;
        this.message = message;
    }


}
