package com.doziem.market_platform.model;

import com.doziem.market_platform.enums.WarehouseType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "central_warehouse")
public class CentralWarehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String  centralWarehouseId;
    private String warehouseName;
    private String address;
    private String city;
    private String state;
    private String country;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "store_id")
    private Store store;
}
