package com.example.simpleformlogin.config;

import com.example.simpleformlogin.service.AuthenticationProviderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationProviderService authenticationProviderService;

    public SecurityConfig(AuthenticationProviderService authenticationProviderService) {
        this.authenticationProviderService = authenticationProviderService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
            .httpBasic(Customizer.withDefaults())
            .formLogin(form -> form.defaultSuccessUrl("/main", true))
            .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
            .authenticationProvider(authenticationProviderService)
        ;

        return http.build();
    }


}
