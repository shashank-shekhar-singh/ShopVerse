package com.shopverse.userservice.domain.entity;

import com.shopverse.userservice.domain.enums.AccountStatus;
import com.shopverse.userservice.domain.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "phoneNumber")
})
public class User extends BaseEntity {

  @Column(name = "first_name", nullable = false)
  private String firstname;

  @Column(name = "last_name")
  private String lastname;

  @Column(name = "phoneNumber", unique = true, nullable = false)
  private String phone;

  @Email
  @Column(nullable = false)
  private String email;

  @Column(name = "gender", nullable = false)
  @Enumerated(EnumType.STRING)
  private Gender gender;

  @Enumerated(EnumType.STRING)
  private AccountStatus status;
}
