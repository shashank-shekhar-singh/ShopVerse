package com.shopverse.authservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class AuthResponse {

    private String accessToken;

    private String refreshToken;

    private String tokenType;

    private long expiresIn;

    private Long userId;

    private String email;

    private Set<String> roles;

    private Set<String> permissions;

    public AuthResponse() {
    }

    public AuthResponse(
            String accessToken,
            String refreshToken,
            String tokenType,
            long expiresIn,
            Long userId,
            String email,
            Set<String> roles,
            Set<String> permissions
    ) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
        this.userId = userId;
        this.email = email;
        this.roles = roles;
        this.permissions = permissions;
    }
}