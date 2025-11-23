package com.doziem.market_platform.model;

import com.doziem.market_platform.enums.DeliveryStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @NotBlank(message = "Dispatch Date is required")
    private ZonedDateTime dispatchDate;
    @NotBlank(message = "Delivery Date is required")
    private ZonedDateTime deliveredDate;
    @NotBlank(message = "Tracking number is required")
    private String trackingNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "logistics_id")
    private LogisticsCompany logisticsCompany;
}
