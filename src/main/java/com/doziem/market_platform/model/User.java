package com.doziem.market_platform.model;

import com.doziem.market_platform.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user-service")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String  userId;
    @Column(unique = true, nullable = false)
    @Email(message = "Provide a valid Email")
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(STRING)
    @Column(nullable = false)
    private Role role;

    private String  phoneNumber;

    @Column(nullable = false)
    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

    private ZonedDateTime lastLogin;

}
