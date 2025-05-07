package com.example.requestmatcher.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/product")
public class ProductController {

    @GetMapping("/{code}")
    public String getProductCode(@PathVariable("code") String code) {
        log.info("==== code={} ====", code);
        return code;
    }
}
