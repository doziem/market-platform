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
    private String stateWarehouseId;

    private String name;
    private String address;
    private String city;
    private String state;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Staff> staffList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "stateWarehouse")
    private List<Product> products = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "branch_id")
    private Store mainBranch;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "central_warehouse_id")
    private CentralWarehouse centralWarehouse;

    public void addProduct(Product product) {
        if (product == null) {
            return;
        }

        if (product.getCentralWarehouse() != null) {
            product.getCentralWarehouse().getProducts().remove(product);
        }

        if (product.getStore() != null) {
            product.getStore().getProducts().remove(product);
        }

        if (!products.contains(product)) {
            products.add(product);
        }

        product.setStateWarehouse(this);
        product.setCentralWarehouse(null);
        product.setStore(null);
    }

    public void removeProduct(Product product) {
        if (product == null) {
            return;
        }

        products.remove(product);
        if (product.getStateWarehouse() == this) {
            product.setStateWarehouse(null);
        }
    }
}
