package com.eugene.sumarry.permission.interceptor;

import com.eugene.sumarry.permission.anno.CheckPerm;
import com.eugene.sumarry.permission.basic.RequestContext;
import org.apache.catalina.connector.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ApiPermInterceptor implements HandlerInterceptor {

    private static final String HASH_TAG = "#";

    private static final Logger logger = LoggerFactory.getLogger(ApiPermInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;

            if (handlerMethod.hasMethodAnnotation(CheckPerm.class)) {
                logger.info("从redis中查看, 当前用户是否有访问此api的权限");
                logger.info("当前权限的key为: {}", buildApiPermKey(handlerMethod));
                logger.info("当前用户id为: {}", RequestContext.getCurrentId());
                handlerReponse(response);
                return false;
            }
        }

        return true;
    }
    
    private void handlerReponse(HttpServletResponse response) throws IOException {
        response.sendError(Response.SC_FORBIDDEN, "No permission access this api");
    }

    private String getCurrentClassName(HandlerMethod handlerMethod) {
        return handlerMethod.getBean().getClass().getSimpleName();
    }

    private String getCurrentMethodsName(HandlerMethod handlerMethod) {
        return handlerMethod.getMethod().getName();
    }

    private String buildApiPermKey(HandlerMethod handlerMethod) {
        return getCurrentClassName(handlerMethod) + HASH_TAG + getCurrentMethodsName(handlerMethod);
    }

}
