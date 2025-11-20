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
public class DeliveryCost {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String  deliveryCostId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private BaseDelivery delivery;

    @NotBlank(message = "Fuel cost is required")
    private double fuelCost;

    @NotBlank(message = "Labor cost is required")
    private double laborCost;

    @NotBlank(message = "tollFee cost is required")
    private double tollFees;

    @NotBlank(message = "Other Expenses is required")
    private double otherExpenses;

    @NotBlank(message = "Total cost is required")
    private double totalCost;
}
