package com.doziem.market_platform.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;


@Table(name = "store_branch")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreBranch {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String branchId;

    @Column(nullable = false)
    private String branchName;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String city;

    private String state;

    private String country;

    private String phoneNumber;

    @ManyToOne(optional = false)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    private boolean mainBranch = false;

    @ManyToOne
    @JoinColumn(name = "state_warehouse_id")
    private StateWarehouse stateWarehouse;


    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="openTime", column=@Column(name="saturday_openTime")),
            @AttributeOverride(name="closeTime", column=@Column(name="saturday_closeTime")),

    })
    private WorkHour saturday;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="openTime", column=@Column(name="sunday_openTime")),
            @AttributeOverride(name="closeTime", column=@Column(name="sunday_closeTime")),

    })
    private WorkHour sunday;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="openTime", column=@Column(name="weekday_openTime")),
            @AttributeOverride(name="closeTime", column=@Column(name="weekday_closeTime")),

    })
    private WorkHour weekday;

    @PrePersist
    protected void onCreate() {
        if (branchId == null) {
            branchId = UUID.randomUUID().toString();
        }
    }
}
