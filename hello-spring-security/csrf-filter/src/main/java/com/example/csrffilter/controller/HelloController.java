package com.example.csrffilter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String getHello() {
        return "Get Hello";
    }

    @PostMapping("/hello")
    public String postHello() {
        return "Post Hello";
    }

    @PostMapping("/non-csrf")
    public String nonCsrf() {
        return "non-csrf";
    }

    @PostMapping("/csrf")
    public String csrf() {
        return "csrf";
    }
}
