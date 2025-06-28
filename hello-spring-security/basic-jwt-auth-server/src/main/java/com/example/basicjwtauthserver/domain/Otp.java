package com.example.basicjwtauthserver.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "otp")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Otp {

    @Id
    @Column(length = 45)
    private String username;

    @Column(length = 45)
    private String code;

    public Otp(String username, String code) {
        this.username = username;
        this.code = code;
    }

    public void updateCode(String code) {
        this.code = code;
    }
}
