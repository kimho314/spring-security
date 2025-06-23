package com.example.basicjwtauthserver.repository;

import com.example.basicjwtauthserver.domain.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpRepository extends JpaRepository<Otp, String> {

}
