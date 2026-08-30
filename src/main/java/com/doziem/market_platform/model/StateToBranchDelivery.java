package com.doziem.market_platform.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StateToBranchDelivery extends BaseDelivery {

    @ManyToOne
    private StateWarehouse stateWarehouse;

    @ManyToOne
    private Store branch;
}
