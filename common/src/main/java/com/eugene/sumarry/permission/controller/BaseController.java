package com.eugene.sumarry.permission.controller;

import com.eugene.sumarry.permission.basic.Message;
import com.eugene.sumarry.permission.exception.BusinessException;
import com.eugene.sumarry.permission.exception.StopProcessRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        } else if (e instanceof StopProcessRuntimeException) {
            errorMessage = e.getMessage();
        } else if (e instanceof MethodArgumentNotValidException) {
            // TODO 需要细化，正确的返回具体哪个参数校验失败以及原因
            e.printStackTrace();
            return Message.bad("参数异常");
        } else if (e instanceof HttpMessageNotReadableException) {
            // TODO 需要细化, 后台请求体接收list, 但是前端list格式不对
            e.printStackTrace();
            return Message.error(e.getMessage());
        }

        logger.error(DEFAULT_ERROR_MESSAGE, e);
        return Message.error(errorMessage);
    }
}
