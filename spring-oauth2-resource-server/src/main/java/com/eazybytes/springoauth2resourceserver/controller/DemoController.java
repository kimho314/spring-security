package com.eazybytes.springoauth2resourceserver.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    @GetMapping(value = "/demo", produces = "application/json")
    public Authentication demo(Authentication authentication){
        return authentication;
    }
}
