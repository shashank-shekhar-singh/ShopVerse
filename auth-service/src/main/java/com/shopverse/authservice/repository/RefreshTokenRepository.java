package com.shopverse.authservice.repository;

import com.shopverse.authservice.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository
        extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByTokenHash(String tokenHash);

    List<RefreshToken> findAllByUserId(Long userId);

    void deleteAllByUserId(Long userId);

    //query that allows us to clean up expired/revoked tokens when we implement token lifecycle/cleanup.
    List<RefreshToken> findAllByExpiresAtBefore(LocalDateTime dateTime);
}
