package com.doziem.market_platform.model;

import com.doziem.market_platform.enums.StoreStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "store-service")
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String  storeId;

    private String storeName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "userId", nullable = false)
    private User user;

    private String storeType;

    private StoreStatus status;

    private String storeLogo;

    private String address;

    private String city;

    private String lga;

    private String state;

    private String country ;

    @Column(unique = true, nullable = false)
    private String  phoneNumber;

    private String zipCode;

    private String countryCode;
    private String iso;

    private boolean isHeadQuarter = false;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<StoreBranch> storeBranches = new ArrayList<>();

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

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

    @PrePersist
    protected void onCreate(){
        createdAt = ZonedDateTime.now();
        status = StoreStatus.PENDING;
    }

    @PreUpdate
    protected void onUpdate(){
        updatedAt = ZonedDateTime.now();
    }

}
