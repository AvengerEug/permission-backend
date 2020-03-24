package com.eugene.sumarry.permission.controller;

import com.eugene.sumarry.permission.basic.Message;
import com.eugene.sumarry.permission.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局处理异常控制器, 但它不是一个正在的控制器
 */
public class BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    private static final String DEFAULT_ERROR_MESSAGE = "系统异常";

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Message exceptionHandler(Exception e) {
        String errorMessage = DEFAULT_ERROR_MESSAGE;
        if (e instanceof BusinessException) {
            errorMessage = e.getMessage();
        }

        logger.error("系统异常", e);
        return Message.error(errorMessage);
    }
}
