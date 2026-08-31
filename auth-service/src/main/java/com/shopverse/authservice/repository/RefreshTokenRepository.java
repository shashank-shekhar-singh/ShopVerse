package com.shopverse.authservice.repository;

import com.shopverse.authservice.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    @Modifying
    @Query("""
    UPDATE RefreshToken rt
    SET rt.revoked = true
    WHERE rt.user.id = :userId
    """)
    void revokeAllByUserId(@Param("userId") Long userId);
}
