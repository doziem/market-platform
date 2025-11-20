package com.doziem.market_platform.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StateReplenishmentItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String  itemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id")
    private StateReplenishmentRequest stateRequest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private int requestedQuantity;
    private int approvedQuantity;
}
