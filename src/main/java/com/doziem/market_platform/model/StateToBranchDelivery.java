package com.doziem.market_platform.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StateToBranchDelivery extends BaseDelivery{

    @ManyToOne
    private StateWarehouse stateWarehouse;

    @ManyToOne
    private StoreBranch branch;
}
