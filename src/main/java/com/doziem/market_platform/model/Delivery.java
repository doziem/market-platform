package com.doziem.market_platform.model;

import com.doziem.market_platform.enums.DeliveryStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String deliveryId;

    @ManyToOne
    private CentralWarehouse warehouse;

    @ManyToOne
    private StoreBranch branch;

    @ManyToOne
    private LogisticsCompany logisticsCompany;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; // PENDING, IN_TRANSIT, DELIVERED, CANCELLED

    private ZonedDateTime dispatchDate;
    private ZonedDateTime estimatedArrivalDate;
    private ZonedDateTime deliveredDate;

    private String trackingNumber;
    private String driverName;
    private String vehicleNumber;
}
