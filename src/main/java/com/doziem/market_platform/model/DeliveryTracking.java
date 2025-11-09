package com.doziem.market_platform.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryTracking {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String deliveryTrackingId;
    @ManyToOne
    private BaseDelivery delivery;
    private String latitude;
    private String longitude;
    private ZonedDateTime timestamp;
}
