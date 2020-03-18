package com.zyb.mini.mall.exception;

import com.zyb.mini.mall.core.Status;

/**
 * @author tanxin
 * @date 2019/8/22
 */
public class ApiException extends RuntimeException {

    private Status status;

    public Status getStatus() {
        return status;
    }

    public ApiException() {
        super();
    }

    public ApiException(Status status, String message) {
        super(message);
        this.status = status;
    }

    public ApiException(Status status) {
        super(status.getMessage());
        this.status = status;
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    protected ApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
