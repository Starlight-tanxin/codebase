package com.dome.mp.server.core;


/**
 * @Auther: XieZhiGui
 * @Date: 2020-4-17
 * @Description: com.zdtx.sdk.core.pojo.dto
 * @version: 1.0
 */
public enum CommonCode implements ResultCode {

    /**
     * 默认成功
     */
    SUCCESS(200, "OK"),

    LOGOUT_SUCCESS(205, "退出成功");

    //操作代码
    private int code;
    //提示信息
    private String message;

    private CommonCode(int code, String message) {

        this.code = code;
        this.message = message;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }}
