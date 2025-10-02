package com.eazybytes.springoauth2customclient.controller;

import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

@RestController
public class DemoController {
    private final OAuth2AuthorizedClientManager clientManager;

    public DemoController(OAuth2AuthorizedClientManager clientManager) {
        this.clientManager = clientManager;
    }

    // Omitted constructor
    @GetMapping(value = "/token", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> token() {
        OAuth2AuthorizeRequest request = OAuth2AuthorizeRequest
                .withClientRegistrationId("1")
                .principal("client2")
                .build();
        OAuth2AuthorizedClient client = clientManager.authorize(request);
        if(Objects.isNull(client)){
            throw new RuntimeException("No authorized client found");
        }

        OAuth2AccessToken accessToken = client.getAccessToken();
        Map<String, String> map = Map.of("access token", accessToken.getTokenValue());
        return map;
    }
}
