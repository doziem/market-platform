package com.doziem.market_platform.model;

import jakarta.persistence.*;
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
    @OneToOne
    private BaseDelivery delivery;
    private double fuelCost;
    private double laborCost;
    private double tollFees;
    private double otherExpenses;
    private double totalCost;
}
