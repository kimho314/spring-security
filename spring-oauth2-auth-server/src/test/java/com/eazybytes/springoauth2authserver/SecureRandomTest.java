package com.eazybytes.springoauth2authserver;

import org.junit.jupiter.api.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class SecureRandomTest {
    @Test
    void generate() throws NoSuchAlgorithmException {
        SecureRandom secureRandom = new SecureRandom();
        byte[] code = new byte[32];
        secureRandom.nextBytes(code);
        String codeVerifier = Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(code);

        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] digested = messageDigest.digest(codeVerifier.getBytes());
        String codeChallenge = Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(digested);
        System.out.println("verifier: " + codeVerifier + " challenge: " + codeChallenge);
    }
}
