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
public class DeliveryProof {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String deliveryProofId;
    @OneToOne
    private BaseDelivery delivery;
    private String recipientName;
    private String signatureUrl;
    private String photoUrl;
    private ZonedDateTime receivedAt;
}
