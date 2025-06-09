package com.example.csrffilter.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.csrf.CsrfToken;

@Slf4j
public class CsrfTokenLogger implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        Object o = request.getAttribute("_csrf");
        CsrfToken csrfToken = (CsrfToken) o;

        log.info("CSRF token: {}", csrfToken.getToken());

        chain.doFilter(request, response);
    }
}
