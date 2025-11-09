package com.doziem.market_platform.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BranchSupplyItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String supplyItemId;

    @ManyToOne
    private BranchSupplyRequest branchSupplyRequest;

    @ManyToOne
    private Product product;

    private int requestedQuantity;
    private int approvedQuantity;
}
