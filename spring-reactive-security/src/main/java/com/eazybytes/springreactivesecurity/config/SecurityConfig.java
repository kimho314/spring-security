package com.eazybytes.springreactivesecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http.httpBasic(Customizer.withDefaults());
        http.authorizeExchange(
                c -> c.pathMatchers(HttpMethod.GET, "/hello") // Selects there quests for which we apply the authorization rules
                        .authenticated()
                        .anyExchange().permitAll()
        );
        return http.build();
    }

    @Bean
    public ReactiveUserDetailsService userDetailsService() {
        var u = User.withUsername("kim")
                .password("{noop}12345")
                .authorities("read")
                .build();
        var uds = new MapReactiveUserDetailsService(u);
        return uds;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
