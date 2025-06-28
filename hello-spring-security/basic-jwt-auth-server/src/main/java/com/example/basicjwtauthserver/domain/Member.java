package com.example.basicjwtauthserver.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

    @Id
    @Column(length = 45)
    private String username;

    private String password;


    public Member(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void updatePassword(String password) {
        this.password = password;
    }
}
