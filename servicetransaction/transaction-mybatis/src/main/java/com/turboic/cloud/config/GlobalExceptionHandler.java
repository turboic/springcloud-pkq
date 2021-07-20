package com.turboic.cloud.config;

import com.turboic.cloud.pojo.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
/**
 * @author liebe
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log =  LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    //@ResponseStatus(reason = "服务请求出现了异常")
    public Message customException(Exception e) {
        log.error("customException");
        Message message = new Message(HttpStatus.INTERNAL_SERVER_ERROR.value(),e.getMessage(),e.getCause().getStackTrace());
        return message;
    }


    @ExceptionHandler(Error.class)
    @ResponseBody
    @ResponseStatus(reason = "系统异常异常")
    public Message customError(Exception e) {
        log.error("customError");
        Message message = new Message(HttpStatus.INTERNAL_SERVER_ERROR.value(),e.getMessage(),"");
        return message;
    }
}
