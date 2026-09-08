package com.shopverse.gateway.security;

import java.util.Set;

public record JwtClaims(
        Long userId,
        Set<String> roles,
        Set<String> permissions
) {

    public JwtClaims {
        roles = roles == null ? Set.of() : Set.copyOf(roles);
        permissions = permissions == null ? Set.of() : Set.copyOf(permissions);
    }
}
