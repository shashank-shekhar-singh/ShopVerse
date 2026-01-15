package com.shopverse.userservice.repository;

import com.shopverse.userservice.domain.entity.Address;
import com.shopverse.userservice.domain.enums.AddressType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
    List<Address> findByUserId(UUID userId);

    Optional<Address> findByIdAndUserId(UUID id, UUID userId);

    @Modifying
    @Query("""
        UPDATE Address a
        SET a.isDefault = false
        WHERE a.customerId = :customerId
          AND a.type = :type
          AND a.isDefault = true
    """)
    void unsetDefaultAddress(
            @Param("customerId") UUID customerId,
            @Param("type") AddressType type
    );
}
