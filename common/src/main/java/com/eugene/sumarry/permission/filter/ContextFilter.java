package com.eugene.sumarry.permission.filter;

import com.eugene.sumarry.permission.basic.RequestContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ContextFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Nothing to do
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        RequestContext.initHttpServletRequestContext((HttpServletRequest) request, (HttpServletResponse) response);

        try {
            chain.doFilter(request, response);
        } finally {
            RequestContext.clearContext();
        }
    }

    @Override
    public void destroy() {
        // Nothing to do
    }
}
