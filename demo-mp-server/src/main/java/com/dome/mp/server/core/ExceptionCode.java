package com.dome.mp.server.core;

/**
 * @Auther: XieZhiGui
 * @Date: 2020-5-24
 * @Description: com.zdtx.common.pojo.result
 * @version: 1.0.0
 */
public enum ExceptionCode implements ResultCode {
    /**
     * 普通错误4 00
     */
    REQUEST_BAD(400, "普通错误"),

    /**
     * 权限相关 4xx
     */
    UNAUTHORIZED(401, "没有授权"),
    FORBIDDEN(402, "没有权限访问"),
    USER_OR_PASSWORD(403, "用户名或密码错误"),
    USER_NOT_EXIST(404, "账户不存在"),
    LOGIN_ERROR(405, "账户登陆失败"),
    TOKEN_ERROR(406, "TOKEN错误"),
    SIGN_ERROR(407, "签名错误"),
    TOKEN_EMPTY(408, "TOKEN为空"),
    SIGN_EMPTY(409, "签名为空"),
    USER_ERROR(410, "账号状态异常"),
    AUTH_ERROR(411, "认证服务端异常"),
    LOGIN_TIMEOUT(412, "登录超时,重新登录"),
    REMEMBER_ERROR(413, "记住我错误"),
    CAPTCHA_ERROR(414, "验证码错误"),
    USER_STOP(415, "账户已停用"),
    TOKEN_EXPIRED(416, "TOKEN已过期"),
    IP_ERROR(417, "登陆的IP不一致"),

    /**
     * 系统相关 5xx
     */
    SYS_ERROR(500, "系统异常"),
    HTTP_REQUEST_ERROR(502, "HTTP请求错误，请检查Method和Content-Type"),
    ROLE_EDIT_ERROR(503, "不允许操作超级管理员角色"),

    /**
     * 数据相关 6xx
     */
    PARAM_LOST(600, "业务参数丢失"),
    DATA_NO_EXIST(601, "数据不存在"),
    PARAM_TYPE_ERROR(602, "参数类型错误"),
    DATA_EXIST(603, "数据已存在"),
    DATA_ADD_FAIL(604, "数据添加失败"),
    ILLEGAL_PARAM(605, "非法参数"),
    MAIN_PARAM_EMPTY(606, "必填参数为空"),
    METHOD_NO_EXIST(607, "方法不存在"),
    METHOD_ERROR(608, "方法错误"),
    EXCEL_ERROR(609, "导出Excel失败，请联系网站管理员"),
    FILE_UPLOAD_ERROR(610, "文件上传失败"),
    HTTP_MEDIA_ERROR(611, "请求数据媒体类型不支持"),
    FILE_UPLOAD_NO_EXIST(612, "找不到上传的文件或者上传的文件为空"),
    ;

    //操作代码
    int code;
    //提示信息
    String message;

    ExceptionCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }
}
