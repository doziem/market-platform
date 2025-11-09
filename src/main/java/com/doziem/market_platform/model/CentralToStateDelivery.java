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
public class CentralToStateDelivery extends BaseDelivery{

    @ManyToOne
    private CentralWarehouse centralWarehouse;

    @ManyToOne
    private StateWarehouse stateWarehouse;
}
