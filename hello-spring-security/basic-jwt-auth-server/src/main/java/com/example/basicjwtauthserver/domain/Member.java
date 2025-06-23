package com.example.basicjwtauthserver.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @Column(length = 45)
    private String username;

    private String password;

    @OneToOne(mappedBy = "member")
    private Otp otp;

    public Member(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
