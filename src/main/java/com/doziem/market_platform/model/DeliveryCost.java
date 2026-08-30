package com.doziem.market_platform.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryCost {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String deliveryCostId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id", nullable = false)
    private BaseDelivery delivery;

    @NotNull(message = "Fuel cost is required")
    @PositiveOrZero
    private double fuelCost;

    @NotNull(message = "Labor cost is required")
    @PositiveOrZero
    private double laborCost;

    @NotNull(message = "Toll fees cost is required")
    @PositiveOrZero
    private double tollFees;

    @NotNull(message = "Other expenses is required")
    @PositiveOrZero
    private double otherExpenses;

    @NotNull(message = "Total cost is required")
    @PositiveOrZero
    private double totalCost;
}
