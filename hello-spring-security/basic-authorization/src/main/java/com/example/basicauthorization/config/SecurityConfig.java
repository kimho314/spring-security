package com.example.basicauthorization.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

//    @Bean
//    public CustomAuthenticationProvider getAuthenticationProvider() {
//        return new CustomAuthenticationProvider();
//    }

    @Bean
    public CustomAuthenticationSuccessHandler getSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    public CustomAuthenticationFailureHandler getFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .httpBasic(basic -> basic.realmName("LUNA")
                .authenticationEntryPoint(new CustomEntryPoint()))
            .formLogin(form -> form
//                .defaultSuccessUrl("/home", false)
                .successHandler(getSuccessHandler())
                .failureHandler(getFailureHandler()))
            .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
//            .authenticationProvider(getAuthenticationProvider())
        ;

        return http.build();
    }
}
