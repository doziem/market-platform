package com.doziem.market_platform.model;

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
    private String name;
    private String address;
    private String city;
    private String state;
    private String country;

    @OneToOne
    private Store store;;
}
