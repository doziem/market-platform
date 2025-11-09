package com.doziem.market_platform.model.staff;

import com.doziem.market_platform.enums.EmploymentStatus;
import com.doziem.market_platform.model.StoreBranch;
import com.doziem.market_platform.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String staffId;

    private String firstName;
    private String lastName;
    private String position;
    private ZonedDateTime hireDate;
    private String  rank;
    @Size(min = 2, message = "Job title must have at least 2 characters")
    private String roleTitle ;

    @OneToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull(message = "User is required")
    private User userAccount;

    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Department department;


    @ManyToOne
    @JoinColumn(name = "branch_id")
    @NotNull(message = "Branch is required")
    private StoreBranch branch;

    @Enumerated(EnumType.STRING)
    private EmploymentStatus status;

    private boolean active;

}
