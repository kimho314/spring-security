package com.example.basicauthlogicserver.auth.config;

import com.example.basicauthlogicserver.auth.model.User;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class AuthenticationServerProxy {

    private final RestClient restClient;

    public AuthenticationServerProxy(RestClient restClient) {
        this.restClient = restClient;
    }

    public void sendAuth(String username, String password) {
        String uri = "/auth";

        User user = new User(username, password);

        ResponseEntity<Void> response = restClient.post()
            .uri(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .body(user)
            .retrieve()
            .toBodilessEntity();
    }

    public boolean sendOTP(String username, String code) {
        String uri = "/otp/check";

        User user = new User(username, code);

        ResponseEntity<Void> responseEntity = restClient.post()
            .uri(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .body(user)
            .retrieve()
            .toBodilessEntity();

        return responseEntity.getStatusCode().is2xxSuccessful();
    }
}
