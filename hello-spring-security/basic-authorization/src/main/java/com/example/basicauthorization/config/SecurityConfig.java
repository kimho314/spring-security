package com.example.basicauthorization.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

//    private final CustomAuthenticationProvider authenticationProvider;
//
//    public SecurityConfig(CustomAuthenticationProvider authenticationProvider) {
//        this.authenticationProvider = authenticationProvider;
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .httpBasic(basic -> basic.realmName("LUNA")
                .authenticationEntryPoint(new CustomEntryPoint()))
            .formLogin(Customizer.withDefaults())
            .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated());
//            .authenticationProvider(authenticationProvider);

        return http.build();
    }
}
