package com.eugene.sumarry.permission.filter;

import com.eugene.sumarry.jwtutil.model.JwtProperty;
import com.eugene.sumarry.permission.basic.Constants;
import com.eugene.sumarry.permission.utils.SpringContextHolder;
import io.jsonwebtoken.Jwts;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthFilter extends OncePerRequestFilter {

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        Boolean isFilter = !request.getRequestURI().equals("/user/login");

        if (isFilter) {

            if (getJwtToken(request) == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "无token");
                return;
            }

            String authToken = request.getHeader(Constants.JWT_TOKEN);
            JwtProperty jwtProperty = SpringContextHolder.getBean(JwtProperty.class);
            Object object = Jwts.parser().setSigningKey(jwtProperty.getSecret()).parseClaimsJws(authToken).getBody();
            logger.info(authToken);
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

}
