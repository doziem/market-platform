package com.doziem.market_platform.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "product-service")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String  productId;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false,unique = true)
    private String sku;

    @Column(nullable = false)
    private String description;

    private Double mrp;

    private Double sellingPrice;

    private String brand;

    private String image;

    private double unitPrice;

    private int quantityInStock;

    private int reorderLevel;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

    @PrePersist
    protected void onCreate(){
        createdAt = ZonedDateTime.now();
    }

    @PreUpdate
    protected void onUpdate(){
        updatedAt = ZonedDateTime.now();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "categoryId", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "central_warehouse_id", referencedColumnName = "centralWarehouseId", nullable = false)
    private CentralWarehouse centralWarehouse;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_warehouse_id", referencedColumnName = "stateWarehouseId")
    private StateWarehouse stateWarehouse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", referencedColumnName = "storeId", nullable = false)
    private Store store;



}
