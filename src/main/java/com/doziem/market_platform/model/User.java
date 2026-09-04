package com.doziem.market_platform.model;

import com.doziem.market_platform.enums.Role;
import com.doziem.market_platform.model.staff.Staff;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user-service")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userId;

    @Column(unique = true, nullable = false)
    @Email(message = "Provide a valid email")
    private String email;

    @Column(nullable = false)
    private String displayName;

    @Column(unique = true, nullable = false,length = 12)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(STRING)
    @Column(nullable = false)
    private Role role;

    private boolean isVerify;

    @Builder.Default
    private Boolean active = true;

    @Column(unique = true)
    private String emailVerificationToken;

    private ZonedDateTime emailVerificationTokenExpiry;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Store> stores = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Staff staff;

    @Column(nullable = false)
    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

    private ZonedDateTime lastLogin;

    @PrePersist
    protected void onCreate() {
        if (userId == null) {
            userId = UUID.randomUUID().toString();
        }
    }

}
