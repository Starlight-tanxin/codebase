package com.zyb.mini.mall.core;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 统一返回结果
 *
 * @author tanxin
 * @date 2019/5/16
 */
@Getter
@Setter
public class R<T> implements Serializable {
    private static final long serialVersionUID = -7119210377216793281L;

    private Integer status;

    private String message;

    private T body;

    private R() {
    }

    private R(Status resultCode) {
        this.status = resultCode.getStatus();
        this.message = resultCode.getMessage();
    }

    public static <T> R<T> success() {
        return new R<>(Status.SUCCESS);
    }

    /**
     * 正常返回
     *
     * @param body 返回的数据
     * @param <T>
     * @return
     */
    public static <T> R<T> success(T body) {
        R<T> result = new R<>(Status.SUCCESS);
        result.setBody(body);
        return result;
    }

    /**
     * 错误返回
     *
     * @param resultCode 错误枚举
     * @return
     */
    public static <T> R<T> error(Status resultCode) {
        return new R<>(resultCode);
    }


    /**
     * 参数丢失错误
     * 自定错误message
     *
     * @param message 错误消息
     * @return
     */
    public static <T> R<T> paramLost(String message) {
        R<T> result = new R<>();
        result.setStatus(Status.PARAM_LOST.getStatus());
        result.setMessage(message);
        return result;
    }

    /**
     * 普通错误
     * 自定错误message
     *
     * @param message
     * @return
     */
    public static <T> R<T> requestBad(String message) {
        R<T> result = new R<>();
        result.setStatus(Status.REQUEST_BAD.getStatus());
        result.setMessage(message);
        return result;
    }
}
