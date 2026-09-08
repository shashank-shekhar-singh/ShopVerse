package com.shopverse.gateway.security;

public final class SecurityConstants {

    public static final String AUTH_API = "/api/auth/**";

    public static final String ROLE_CLAIM = "roles";
    public static final String PERMISSION_CLAIM = "permissions";
    public static final String USER_ID_CLAIM = "userId";

    private SecurityConstants() {
    }
}
