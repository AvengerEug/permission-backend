package com.eugene.sumarry.permission.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.ConcurrentHashMap;

public class RequestContext extends ConcurrentHashMap<String, Object> {

    private static final Logger logger = LoggerFactory.getLogger(RequestContext.class);

    private static final ThreadLocal<? extends RequestContext> threadLocal = new ThreadLocal<RequestContext>() {

        // threadLocal.get()方法获取不到对象时，会将此方法的值返回出去
        // 并将返回出来的对象放入threadLocal中
        protected RequestContext initialValue() {
            try {
                return Hodler.getInstance();
            } catch (Throwable var2) {
                throw new RuntimeException(var2);
            }
        }
    };

    public static RequestContext getCurrentContext() {
        return threadLocal.get();
    }

    public static void clearContext() {
        RequestContext requestContext = getCurrentContext();
        if (requestContext != null) {
            threadLocal.remove();
        }
    }
    
    private static class Hodler {
        ;
        
        private static RequestContext requestContext = new RequestContext();

        public static RequestContext getInstance() {
            return requestContext;
        }
    }

    public static Object getAttribute(String key) {
        return getCurrentContext().get(key);
    }

    public static void setAttribute(String key, Object value) {
        getCurrentContext().put(key, value);
    }

    public static void initHttpServletRequestContext(HttpServletRequest request, HttpServletResponse response) {
        setAttribute(Constants.REQUEST, request);
        setAttribute(Constants.RESPONSE, response);
    }

    public static Object getHttpServletRequestParam(String param) {
        return getHttpServletRequest().getParameter(param);
    }

    public static HttpServletRequest getHttpServletRequest() {
        return (HttpServletRequest) getAttribute(Constants.REQUEST);
    }

    public static void saveCurrentId(Object currentId) {
        setAttribute(Constants.USER_ID, currentId);
    }

    public static Object getCurrentId() {
        return getAttribute(Constants.USER_ID);
    }

}
