package com.eazybytes.springreactivesecurity.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class DemoController {
    @GetMapping(value = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> demo(){
        return Mono.just("Hello!");
    }
}
