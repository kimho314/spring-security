package com.example.requestmatcher.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        UserDetails user1 = User.withUsername("john")
            .password("{noop}12345")
            .roles("ADMIN")
            .build();
        UserDetails user2 = User.withUsername("jane")
            .password("{noop}12345")
            .roles("MANAGER")
            .build();

        manager.createUser(user1);
        manager.createUser(user2);

        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // 권한 설정을 안해주면 기본적으로 모든 유저에 대햇 접근 불가하다.
    // 모든 유저에 대햇 접근 허용해주고 싶으면 .anyRequest().permitAll() 사용하면 된다.
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .httpBasic(Customizer.withDefaults())
            .authorizeHttpRequests((auth) -> auth.requestMatchers("/hello").hasRole("ADMIN")
                .requestMatchers("/ciao").hasRole("MANAGER")
                .anyRequest()
//                .authenticated() // 인증된 사용자만 접근하도록 설정한다.
                .permitAll());

        return http.build();
    }
}
