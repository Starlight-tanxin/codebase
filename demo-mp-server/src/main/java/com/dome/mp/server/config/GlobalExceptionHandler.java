package com.dome.mp.server.config;

import com.dome.mp.server.core.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;


/**
 * 全局异常处理
 *
 * @author tanxin
 */
@Slf4j
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {


    @ExceptionHandler(value = {Exception.class, SQLIntegrityConstraintViolationException.class, DuplicateKeyException.class})
    public Result<?> handle(Exception e) {
        log.error("全局异常处理捕获到的错误：", e);
        if (e instanceof HttpMessageNotReadableException) {
            HttpMessageNotReadableException httpMessageNotReadableException = (HttpMessageNotReadableException) e;
            log.error("JSON格式不正确 ==》 错误 : {} \n\t Message : {}", httpMessageNotReadableException.getHttpInputMessage(), httpMessageNotReadableException.getMessage());
        } else if (e instanceof HttpMediaTypeNotSupportedException) {
            HttpMediaTypeNotSupportedException httpMediaTypeNotSupportedException = (HttpMediaTypeNotSupportedException) e;
            log.error("HTTP请求头错误 ==》 错误 : {} \n\t Message : {}", httpMediaTypeNotSupportedException, httpMediaTypeNotSupportedException.getMessage());
        } else if (e instanceof SQLIntegrityConstraintViolationException) {
            SQLIntegrityConstraintViolationException sqlException = (SQLIntegrityConstraintViolationException) e;
            log.error("唯一标识重复错误 ==》 错误 : {} \n\t Message : {}", sqlException, sqlException.getMessage());
        } else if (e instanceof DuplicateKeyException) {
            DuplicateKeyException sqlException = (DuplicateKeyException) e;
            log.error("唯一标识重复错误 ==》 错误 : {} \n\t Message : {}", sqlException, sqlException.getMessage());
        } else if (e instanceof NullPointerException) {
            e.printStackTrace();
            NullPointerException nullPointerException = (NullPointerException) e;
            log.error("空指针异常 ==》 错误 : {} \n\t Message : {}", nullPointerException, nullPointerException.getMessage());
        } else if (e instanceof ConstraintViolationException) {
            ConstraintViolationException exception = (ConstraintViolationException) e;
            log.error("传参错误 ==》 错误 : {} \n\t Message : {}", exception, exception.getMessage());
        } else {
            log.error("其他异常 ==》错误 : {} \n\t Message : {}", e, e.getMessage());
        }
        return Result.requestBad(e.getMessage());
    }
}
