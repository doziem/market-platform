package com.doziem.market_platform.model;

import com.doziem.market_platform.enums.DeliveryStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class BaseDelivery {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String deliveryId;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;
    private ZonedDateTime dispatchDate;
    private ZonedDateTime deliveredDate;
    private String trackingNumber;

    @ManyToOne
    private LogisticsCompany logisticsCompany;
}
