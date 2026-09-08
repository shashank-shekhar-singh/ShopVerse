package com.shopverse.gateway.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;

import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Configuration
public class JwtTokenValidator {

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.public-key}")
    private String publicKeyPem;

    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        RSAPublicKey publicKey = loadPublicKey(publicKeyPem);

        NimbusReactiveJwtDecoder decoder =
                NimbusReactiveJwtDecoder.withPublicKey(publicKey).build();

        OAuth2TokenValidator<Jwt> issuerAndTimeValidator =
                JwtValidators.createDefaultWithIssuer(issuer);

        decoder.setJwtValidator(
                new DelegatingOAuth2TokenValidator<>(issuerAndTimeValidator)
        );

        return decoder;
    }

    private RSAPublicKey loadPublicKey(String pem) {
        try {
            String key = pem
                    .replace("\\n", "\n")
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s", "");

            byte[] keyBytes = Base64.getDecoder().decode(key);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load JWT public key", e);
        }
    }
}
