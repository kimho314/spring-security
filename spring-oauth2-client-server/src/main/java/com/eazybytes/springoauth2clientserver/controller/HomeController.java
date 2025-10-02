package com.eazybytes.springoauth2clientserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class HomeController {
    @GetMapping("/")
    public String home(OAuth2AuthenticationToken authentication) {
        log.info("auth: {}", authentication);
        String info = """
                credentials: %s
                name: %s
                attributes: %s
                authorities: %s
                registrationId: %s
                """.formatted(authentication.getCredentials().toString(),
                authentication.getPrincipal().getName().toString(),
                authentication.getPrincipal().getAttributes().toString(),
                authentication.getPrincipal().getAuthorities().toString(),
                authentication.getAuthorizedClientRegistrationId().toString());
        log.info(info);

        return "index.html";
    }
}
