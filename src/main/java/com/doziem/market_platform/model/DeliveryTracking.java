package com.doziem.market_platform.model;

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
public class DeliveryTracking {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String deliveryTrackingId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private BaseDelivery delivery;
    @NotBlank(message = "Latitude is required")

    private String latitude;
    @NotBlank(message = "Longitude is required")
    private String longitude;

    private ZonedDateTime timestamp;
}
