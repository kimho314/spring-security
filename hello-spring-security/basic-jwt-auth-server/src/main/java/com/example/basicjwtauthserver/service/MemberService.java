package com.example.basicjwtauthserver.service;

import com.example.basicjwtauthserver.domain.Member;
import com.example.basicjwtauthserver.domain.Otp;
import com.example.basicjwtauthserver.repository.MemberRepository;
import com.example.basicjwtauthserver.repository.OtpRepository;
import com.example.basicjwtauthserver.utils.GenerateCodeUtil;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final OtpRepository otpRepository;

    @Transactional
    public void createMember(Member member) {
        member.updatePassword(passwordEncoder.encode(member.getPassword()));
        memberRepository.save(member);
    }

    @Transactional
    public void auth(Member member) {
        Optional<Member> maybeMember = memberRepository.findByUsername(member.getUsername());

        if (maybeMember.isPresent()) {
            Member foundMember = maybeMember.get();
            if (passwordEncoder.matches(member.getPassword(), foundMember.getPassword())) {
                renewOtp(foundMember);
            } else {
                throw new BadCredentialsException("Bad credentials.");
            }
        } else {
            throw new BadCredentialsException("Bad credentials.");
        }
    }

    @Transactional
    public void renewOtp(Member foundMember) {
        String code = GenerateCodeUtil.generateCode();
        Optional<Otp> maybeOtp = otpRepository.findByUsername(foundMember.getUsername());
        if (maybeOtp.isPresent()) {
            Otp otp = maybeOtp.get();
            otp.updateCode(code);
        } else {
            Otp otp = new Otp(foundMember.getUsername(), code);
            otpRepository.save(otp);
        }
    }

    @Transactional(readOnly = true)
    public boolean check(Otp otpToValidate) {
        Optional<Otp> maybeOtp = otpRepository.findByUsername(otpToValidate.getUsername());
        if (maybeOtp.isPresent()) {
            Otp otp = maybeOtp.get();
            return otp.getCode().equals(otpToValidate.getCode());
        }

        return false;
    }
}
