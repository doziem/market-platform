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

    @ManyToOne
    private StateReplenishmentRequest stateRequest;

    @ManyToOne
    private Product product;

    private int requestedQuantity;
    private int approvedQuantity;
}
