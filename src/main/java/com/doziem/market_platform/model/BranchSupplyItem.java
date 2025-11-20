package com.doziem.market_platform.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_supply_request_id")
    private BranchSupplyRequest branchSupplyRequest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    @NotBlank(message = "Requested quantity is required")
    private int requestedQuantity;
    @NotBlank(message = "Approved quantity is required")
    private int approvedQuantity;
}
