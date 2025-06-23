package com.example.basicjwtauthserver.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "otp")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Otp {

    @Id
    @Column(length = 45)
    private String username;

    @Column(length = 45)
    private String code;

    @OneToOne
    @MapsId
    @JoinColumn(name = "username")
    private Member member;
}
