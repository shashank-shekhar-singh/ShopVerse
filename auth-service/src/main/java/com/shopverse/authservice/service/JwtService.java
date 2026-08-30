package com.shopverse.authservice.service;

import com.shopverse.authservice.config.JwtConfig;
import com.shopverse.authservice.security.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class JwtService {

    private final JwtConfig jwtConfig;
    private final PrivateKey privateKey;
    private final PublicKey publicKey;

    public JwtService(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
        this.privateKey = loadPrivateKey(jwtConfig.getPrivateKey());
        this.publicKey = loadPublicKey(jwtConfig.getPublicKey());
    }

    public String generateAccessToken(CustomUserDetails userDetails) {

        Date issuedAt = new Date();
        Date expiration = new Date(
                issuedAt.getTime() + jwtConfig.getAccessTokenExpirationMs()
        );

        Set<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .filter(authority -> authority.startsWith("ROLE_"))
                .collect(Collectors.toSet());

        Set<String> permissions = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .filter(authority -> !authority.startsWith("ROLE_"))
                .collect(Collectors.toSet());

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuer(jwtConfig.getIssuer())
                .issuedAt(issuedAt)
                .expiration(expiration)
                .claim("userId", userDetails.getUserId())
                .claim("roles", roles)
                .claim("permissions", permissions)
                .signWith(privateKey, Jwts.SIG.RS256)
                .compact();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public Long extractUserId(String token) {
        Number userId = extractAllClaims(token).get("userId", Number.class);

        return userId != null ? userId.longValue() : null;
    }

    @SuppressWarnings("unchecked")
    public Set<String> extractRoles(String token) {
        List<String> roles = extractAllClaims(token)
                .get("roles", List.class);

        if (roles == null) {
            return Set.of();
        }

        return Set.copyOf(roles);
    }

    @SuppressWarnings("unchecked")
    public Set<String> extractPermissions(String token) {
        List<String> permissions = extractAllClaims(token)
                .get("permissions", List.class);

        if (permissions == null) {
            return Set.of();
        }

        return Set.copyOf(permissions);
    }

    public boolean validateToken(String token) {
        try {
            Claims claims = extractAllClaims(token);

            return jwtConfig.getIssuer().equals(claims.getIssuer())
                    && claims.getExpiration().after(new Date());

        } catch (IllegalArgumentException
                 | io.jsonwebtoken.JwtException e) {

            return false;
        }
    }

    public long getAccessTokenExpirationMs() {
        return jwtConfig.getAccessTokenExpirationMs();
    }

    private Claims extractAllClaims(String token) {

        Jws<Claims> claimsJws = Jwts.parser()
                .verifyWith(publicKey)
                .requireIssuer(jwtConfig.getIssuer())
                .build()
                .parseSignedClaims(token);

        return claimsJws.getPayload();
    }

    private PrivateKey loadPrivateKey(String pem) {

        try {
            String key = cleanPem(pem);

            byte[] keyBytes = Base64.getDecoder().decode(key);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            EncodedKeySpec keySpec =
                    new PKCS8EncodedKeySpec(keyBytes);

            return keyFactory.generatePrivate(keySpec);

        } catch (Exception e) {
            throw new IllegalStateException(
                    "Failed to load JWT private key",
                    e
            );
        }
    }

    private PublicKey loadPublicKey(String pem) {

        try {
            String key = cleanPem(pem);

            byte[] keyBytes = Base64.getDecoder().decode(key);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            EncodedKeySpec keySpec =
                    new X509EncodedKeySpec(keyBytes);

            return keyFactory.generatePublic(keySpec);

        } catch (Exception e) {
            throw new IllegalStateException(
                    "Failed to load JWT public key",
                    e
            );
        }
    }

    private String cleanPem(String pem) {

        return pem
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");
    }
}