package com.example.simpleformlogin.service;

import com.example.simpleformlogin.entity.CustomUserDetails;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthenticationProviderService implements AuthenticationProvider {

    private final JpaUserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SCryptPasswordEncoder scryptPasswordEncoder;

    public AuthenticationProviderService(JpaUserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder,
        SCryptPasswordEncoder scryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.scryptPasswordEncoder = scryptPasswordEncoder;
    }

    @Transactional(readOnly = true)
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        CustomUserDetails user = userDetailsService.loadUserByUsername(username);

        Authentication auth;
        switch (user.getMember().getAlgorithm()) {
            case BCRYPT -> auth = checkPassword(user, password, bCryptPasswordEncoder);
            case SCRYPT -> auth = checkPassword(user, password, scryptPasswordEncoder);
            default -> throw new BadCredentialsException("Bad credentials");
        }

        return auth;
    }

    private Authentication checkPassword(CustomUserDetails user, String rawPassword, PasswordEncoder passwordEncoder) {
        if (passwordEncoder.matches(rawPassword, user.getPassword())) {
            return new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                user.getPassword(),
                user.getAuthorities()
            );
        } else {
            throw new BadCredentialsException("Bad credentials");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
