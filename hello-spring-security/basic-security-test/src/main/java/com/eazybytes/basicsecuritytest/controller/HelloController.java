package com.eazybytes.basicsecuritytest.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello(Authentication authentication) {
        log.info("name : {}", authentication.getName());
        return "hello";
    }
}
