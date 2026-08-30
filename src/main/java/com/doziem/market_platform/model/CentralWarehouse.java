package com.doziem.market_platform.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
    @NotBlank(message = "Warehouse name is required")
    private String warehouseName;
    @NotBlank(message = "Warehouse type is required")
    private String address;
    @NotBlank(message = "City is required")
    private String city;
    @NotBlank(message = "State is required")
    private String state;
    @NotBlank(message = "Country is required")
    private String country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @OneToMany(mappedBy = "centralWarehouse", cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();;

    public void addProduct(Product product) {
        if (product == null) {
            return;
        }

        if (product.getStateWarehouse() != null) {
            product.getStateWarehouse().getProducts().remove(product);
        }

        if (product.getStore() != null) {
            product.getStore().getProducts().remove(product);
        }

        if (!products.contains(product)) {
            products.add(product);
        }

        product.setCentralWarehouse(this);
        product.setStateWarehouse(null);
        product.setStore(null);
    }

    public void removeProduct(Product product) {
        if (product == null) {
            return;
        }

        products.remove(product);
        if (product.getCentralWarehouse() == this) {
            product.setCentralWarehouse(null);
        }
    }
}
