package com.example.customfilter.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

@Slf4j
public class RequestValidationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String requestId = httpRequest.getHeader("Request-Id");
        log.info("requestId={}", requestId);

        if (!StringUtils.hasText(requestId)) {
            httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
