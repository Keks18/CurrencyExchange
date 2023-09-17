package com.project.project_3_currencyexchange.controllers;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"}, initParams = {
        @WebInitParam(name = "encoding", value = "UTF-8"),
        @WebInitParam(name = "type", value = "application/json")
})
public class EncodingFilter implements Filter {
    private String encoding;
    private String type;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        encoding = filterConfig.getInitParameter("encoding");
        type = filterConfig.getInitParameter("type");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        request.setCharacterEncoding(encoding);
        response.setCharacterEncoding(encoding);
        response.setContentType(type);
        chain.doFilter(request, response);
    }
}
