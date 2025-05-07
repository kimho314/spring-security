package com.example.requestmatcher.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @PostMapping("/a")
    public String postEndPointA() {
        return "postEndPointA";
    }

    @GetMapping("/a")
    public String getEndPointA() {
        return "getEndPointA";
    }

    @GetMapping("/a/b")
    public String getEndPointB() {
        return "getEndPointB";
    }

    @GetMapping("/a/b/c")
    public String getEndPointC() {
        return "getEndPointC";
    }
}
