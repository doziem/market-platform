package com.doziem.market_platform.model.staff;

import com.doziem.market_platform.enums.EmploymentStatus;
import com.doziem.market_platform.model.StateWarehouse;
import com.doziem.market_platform.model.Store;
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
    @Column(name = "staff_id")
    private String staffId;

    private String firstName;
    private String lastName;
    private String position;
    private ZonedDateTime hireDate;
    private String  rank;
    @Size(min = 2, message = "Job title must have at least 2 characters")
    private String roleTitle ;

    @Enumerated(EnumType.STRING)
    private EmploymentStatus status;

    private boolean active;

    @OneToOne(optional = false,  fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Store branch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_warehouse_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private StateWarehouse stateWarehouse;


}
