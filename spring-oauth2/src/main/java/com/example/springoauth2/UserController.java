package com.example.springoauth2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @GetMapping("/v1/users/me")
    public UserInfoResponse getMyInfo() {
        return new UserInfoResponse("hoseop");
    }


    public record UserInfoResponse(String nickname) {

    }
}
