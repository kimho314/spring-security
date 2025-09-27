package com.eazybytes.springoauth2resourceserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Value(value = "${keySetUri}")
    private String keySetUri;

    @Value("${introspectionUri}")
    private String introspectionUri;
    @Value("${resourceserver.clientID}")
    private String resourceServerClientID;
    @Value("${resourceserver.secret}")
    private String resourceServerSecret;

    private final JwtAuthenticationConverter converter;

    public SecurityConfig(JwtAuthenticationConverter authenticationConverter) {
        this.converter = authenticationConverter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.oauth2ResourceServer(
//                c -> c.jwt(j -> j.jwkSetUri(keySetUri)// configuring the public key set URI
//                                .jwtAuthenticationConverter(converter))
//        );

        // configuring the credentials the resource server must use to authenticate when calling the authorization server’s introspection URI
        http.oauth2ResourceServer(c -> c.opaqueToken(o -> o.introspectionUri(introspectionUri)
                .introspectionClientCredentials(resourceServerClientID, resourceServerSecret)));

        http.authorizeHttpRequests(
                c -> c.anyRequest().authenticated()
        );

        return http.build();
    }
}
