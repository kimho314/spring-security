package com.example.requestmatcher.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
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
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

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
            .roles("MANAGER", "PREMIUM")
            .build();

        manager.createUser(user1);
        manager.createUser(user2);

        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    @Bean
    @Order(1)
    public SecurityFilterChain configure1(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .httpBasic(Customizer.withDefaults())
            .securityMatcher("/hello", "/ciao", "/hola")
            .authorizeHttpRequests((auth) -> auth.requestMatchers("/hello").hasRole("ADMIN")
                .requestMatchers("/ciao").hasRole("MANAGER")
                .anyRequest()
                .authenticated() // 인증된 사용자만 접근하도록 설정한다.
            )
        ;

        return http.build();
    }

    // 권한 설정을 안해주면 기본적으로 모든 유저에 대햇 접근 불가하다.
    // 모든 유저에 대햇 접근 허용해주고 싶으면 .anyRequest().permitAll() 사용하면 된다.

    @Bean
    @Order(2)
    public SecurityFilterChain configure2(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .httpBasic(Customizer.withDefaults())
            .securityMatcher("/a/**")
            .authorizeHttpRequests((auth) -> auth
                    .requestMatchers(HttpMethod.POST, "/a")
                    .permitAll() // POST /a 호출은 모든 유저 접근 허용
                    .requestMatchers(HttpMethod.GET, "/a")
                    .authenticated() // GET /a 호출은 인증 통과한 유저만 접근 허용
                    .requestMatchers("/a/b/**")
                    .authenticated() // /a/b 로 시작하는 모든 url은 인증 통과한 튜어만이 접근 허용
                    .anyRequest()
//                .authenticated() // 인증된 사용자만 접근하도록 설정한다.
//                .permitAll()
                    .denyAll()
            )
        ;

        return http.build();
    }

    @Order(3)
    @Bean
    public SecurityFilterChain configure3(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .httpBasic(Customizer.withDefaults())
            .securityMatcher("/product/**")
            .authorizeHttpRequests((auth) -> auth
                .requestMatchers("/product/{code:^[0-9]*$}").permitAll()
                .anyRequest().denyAll()
            )
        ;

        return http.build();
    }

    @Order(4)
    @Bean
    public SecurityFilterChain configure4(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .httpBasic(Customizer.withDefaults())
            .securityMatcher("/video/**")
            .authorizeHttpRequests((auth) -> auth
                .requestMatchers(
                    new RegexRequestMatcher(".*/(us|uk|ca)+/(en|fr).*", HttpMethod.GET.name()))
                .authenticated()
                .anyRequest().hasRole("PREMIUM")
            )
        ;

        return http.build();
    }
}
