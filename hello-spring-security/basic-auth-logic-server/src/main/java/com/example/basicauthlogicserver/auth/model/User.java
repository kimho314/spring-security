package com.example.basicauthlogicserver.auth.model;

public record User(String username, String password, String code) {

    public User(String username, String password) {
        this(username, password, null);
    }
}
