package com.eugene.sumarry.permission.filter;

import com.eugene.sumarry.jwtutil.entry.JwtSign;
import com.eugene.sumarry.jwtutil.model.JwtProperty;
import com.eugene.sumarry.permission.basic.Constants;
import com.eugene.sumarry.permission.basic.RequestContext;
import com.eugene.sumarry.permission.utils.SpringContextHolder;
import io.jsonwebtoken.Jwts;
import org.springframework.boot.SpringApplication;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class JWTAuthFilter extends OncePerRequestFilter {

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        Boolean isFilter = !request.getRequestURI().equals("/user/login");

        if (isFilter) {

            if (getJwtToken(request) == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "无token");
                return;
            }

            String jwtToken = request.getHeader(Constants.JWT_TOKEN);
            saveCurrentId(jwtToken);
            logger.info(jwtToken);
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Nothing to do
    }

    private String getJwtToken(HttpServletRequest request) {
        return request.getHeader(Constants.JWT_TOKEN);
    }

    private void saveCurrentId(String jwtToken) {
        JwtSign jwtSign = SpringContextHolder.getBean(JwtSign.class);
        Map<String, Object> info = (Map<String, Object>) jwtSign.unSignBody(jwtToken);

        Set<String> keys = info.keySet();
        Iterator<String> iterator = keys.iterator();
        // 第一个key对应的value为自定义数据
        String key = iterator.hasNext() ? iterator.next() : null;

        if (key != null) {
            Map<String, Object> map = (Map)info.get(key);
            Integer userId = (Integer)map.get(Constants.USER_ID);
            RequestContext.saveCurrentId(userId.longValue());
        }
    }

}
