package com.doziem.market_platform.model;

import com.doziem.market_platform.enums.DeliveryStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "central_warehouse_id")
    private CentralWarehouse warehouse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private Store branch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "logistic_id")
    private LogisticsCompany logisticsCompany;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;
    @NotBlank(message = "Dispatch date is required")
    private ZonedDateTime dispatchDate;
    @NotBlank(message = "Estimated Date of arrival is required")
    private ZonedDateTime estimatedArrivalDate;

    private ZonedDateTime deliveredDate;

    @NotBlank(message = "Tracking number is required")
    private String trackingNumber;
    private String driverName;
    private String vehicleNumber;
}
