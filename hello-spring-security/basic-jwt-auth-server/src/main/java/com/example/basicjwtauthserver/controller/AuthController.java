package com.example.basicjwtauthserver.controller;

import com.example.basicjwtauthserver.domain.Member;
import com.example.basicjwtauthserver.domain.Otp;
import com.example.basicjwtauthserver.dto.AuthMember;
import com.example.basicjwtauthserver.dto.CheckOtp;
import com.example.basicjwtauthserver.dto.CreateMemberDto;
import com.example.basicjwtauthserver.service.MemberService;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;

    @PostMapping("/members")
    public ResponseEntity<Void> createMember(@RequestBody CreateMemberDto createMemberDto) {
        Member member = new Member(createMemberDto.username(), createMemberDto.password());
        memberService.createMember(member);
        return ResponseEntity.created(URI.create("")).build();
    }

    @PostMapping("/auth")
    public ResponseEntity<Void> auth(@RequestBody AuthMember authMember) {
        Member member = new Member(authMember.username(), authMember.password());
        memberService.auth(member);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/otp/check")
    public ResponseEntity<Void> checkOtp(@RequestBody CheckOtp checkOtp) {
        Otp otp = new Otp(checkOtp.username(), checkOtp.code());
        if (memberService.check(otp)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
