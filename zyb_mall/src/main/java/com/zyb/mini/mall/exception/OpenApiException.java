package com.zyb.mini.mall.exception;

import com.zyb.mini.mall.core.Status;

/**
 * @author tanxin
 * @date 2019/8/13
 */
public class OpenApiException extends RuntimeException {

    private Status status;

    public Status getStatus() {
        return status;
    }

    public OpenApiException() {
        super();
    }

    public OpenApiException(Status status, String message) {
        super(message);
        this.status = status;
    }

    public OpenApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public OpenApiException(Throwable cause) {
        super(cause);
    }

    protected OpenApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
