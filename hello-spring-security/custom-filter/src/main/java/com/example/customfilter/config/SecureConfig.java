package com.example.customfilter.config;

import com.example.customfilter.filter.AuthenticationLoggingFilterWithOncePerRequestFilter;
import com.example.customfilter.filter.RequestValidationFilter;
import com.example.customfilter.filter.StaticKeyAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecureConfig {

    private final StaticKeyAuthenticationFilter staticKeyAuthenticationFilter;

    public SecureConfig(StaticKeyAuthenticationFilter staticKeyAuthenticationFilter) {
        this.staticKeyAuthenticationFilter = staticKeyAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(
                new RequestValidationFilter(),
                BasicAuthenticationFilter.class)
            .addFilterAfter(
                new AuthenticationLoggingFilterWithOncePerRequestFilter(),
                BasicAuthenticationFilter.class)
//            .addFilterAt(staticKeyAuthenticationFilter, BasicAuthenticationFilter.class)
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

        return http.build();
    }
}
