package com.eazybytes.springoauth2resourceserver.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.authentication.OpaqueTokenAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.introspection.SpringOpaqueTokenIntrospector;
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
//        http.oauth2ResourceServer(c -> c.opaqueToken(o -> o.introspectionUri(introspectionUri)
//                .introspectionClientCredentials(resourceServerClientID, resourceServerSecret)));

        // configuring multitenancy
        http.oauth2ResourceServer(
                c -> c.authenticationManagerResolver(
                        authenticationManagerResolver(
                                jwtDecoder(),
                                opaqueTokenIntrospector()
                        )
                )
        );

        http.authorizeHttpRequests(
                c -> c.anyRequest().authenticated()
        );

        return http.build();
    }

    @Bean
    public AuthenticationManagerResolver<HttpServletRequest> authenticationManagerResolver(
            JwtDecoder jwtDecoder,
            OpaqueTokenIntrospector opaqueTokenIntrospector
    ) {
        // Defining an authentication manager for the authorization server managing JWT access tokens
        AuthenticationManager jwtAuth = new ProviderManager(
                new JwtAuthenticationProvider(jwtDecoder)
        );
        // Defining another authentication manager for the authorization server managing opaque tokens
        AuthenticationManager opaqueAuth = new ProviderManager(
                new OpaqueTokenAuthenticationProvider(opaqueTokenIntrospector)
        );
        
        return (request) -> {
            if ("jwt".equals(request.getHeader("type"))) {
                return jwtAuth;
            }
            else {
                return opaqueAuth;
            }
        };
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder
                .withJwkSetUri("http://localhost:9000/oauth2/jwks")
                .build();
    }

    @Bean
    public OpaqueTokenIntrospector opaqueTokenIntrospector() {
        return SpringOpaqueTokenIntrospector.withIntrospectionUri("http://localhost:9001/oauth2/introspect")
                .clientId("credential-client2")
                .clientSecret("secret3")
                .build();
    }
}
