package com.doziem.market_platform.model;

import com.doziem.market_platform.model.staff.Staff;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


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
    private Staff managerName;

    @OneToMany(mappedBy = "stateWarehouse")
    private List<Product> products = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "main_branch_id")
    private StoreBranch mainBranch;

    @ManyToOne
    @JoinColumn(name = "central_warehouse_id")
    private CentralWarehouse centralWarehouse;
}
