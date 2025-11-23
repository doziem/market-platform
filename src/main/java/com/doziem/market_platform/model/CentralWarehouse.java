package com.doziem.market_platform.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

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

    @OneToMany(mappedBy = "centralWarehouse", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> product;
}
