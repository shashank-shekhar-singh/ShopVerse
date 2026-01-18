package com.shopverse.userservice.domain.entity;


import com.shopverse.userservice.domain.enums.AddressType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Validated
@Entity
@Table(name = "addresses")
public class Address extends BaseEntity{

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Enumerated(EnumType.STRING)
    private AddressType type;

    @Column(name = "is_default", nullable = false)
    private boolean isDefault;

    private String line1;
    private String city;
    private String state;
    private String country;
    private String pincode;
}
