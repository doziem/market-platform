package com.doziem.market_platform.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CentralToStateDelivery extends BaseDelivery{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "central_warehouse_id")
    private CentralWarehouse centralWarehouse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_warehouse_id")
    private StateWarehouse stateWarehouse;
}
