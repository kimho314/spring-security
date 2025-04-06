package com.example.basicauthorization.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/hello/security-context-holder")
    public String helloSecurityContextHolder(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        return "Hello " + authentication.getName();
    }

    @GetMapping("/hello/authentication-parameter")
    public String helloAuthenticationParameter(Authentication authentication){
        return "Hello " + authentication.getName();
    }
}
