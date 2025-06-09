package com.example.csrffilter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/login")
    public String login() {
        return "login.html";
    }

    @GetMapping("/main")
    public String maine() {
        return "main.html";
    }
}
