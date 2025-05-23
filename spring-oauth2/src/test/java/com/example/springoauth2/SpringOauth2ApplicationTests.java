package com.example.springoauth2;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Base64;

@SpringBootTest
class SpringOauth2ApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void generateAuthorizationBasicHeader() {
        String clientId = "test-client";
        String clientSecret = "test";
        String encode = clientId + ":" + clientSecret;
        String encodeToString = Base64.getEncoder().encodeToString(encode.getBytes());
        System.out.println(encodeToString);
    }

}
