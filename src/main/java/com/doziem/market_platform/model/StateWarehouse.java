package com.doziem.market_platform.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StateWarehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String  stateWarehouseId;

    private String name;
    private String address;
    private String city;
    private String state;

    @OneToOne
    private User managerName;

    @OneToOne
    @JoinColumn(name = "main_branch_id")
    private StoreBranch mainBranch;

    @ManyToOne
    @JoinColumn(name = "central_warehouse_id")
    private CentralWarehouse centralWarehouse;
}
