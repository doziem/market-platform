package com.doziem.market_platform.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    private String productId;

    @NotBlank(message = "Product name is required")
    @Column(nullable = false)
    private String productName;

    @NotBlank(message = "SKU is required")
    @Column(nullable = false, unique = true)
    private String sku;

    @NotBlank(message = "Description is required")
    @Column(nullable = false)
    private String description;

    private Double mrp;

    @NotNull(message = "Selling Price is required")
    private Double sellingPrice;

    @NotBlank(message = "Brand is required")
    private String brand;

    @NotBlank(message = "Image is required")
    private String image;

    @NotNull(message = "Unit price is required")
    private Double unitPrice;

    @NotNull(message = "Quantity in stock is required")
    private Integer quantityInStock;

    @NotNull(message = "Reorder level is required")
    private Integer reorderLevel;

    @Column(nullable = false, updatable = false)
    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = ZonedDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = ZonedDateTime.now();
    }

    // -------------------- RELATIONSHIPS --------------------

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "categoryId", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "central_warehouse_id", referencedColumnName = "centralWarehouseId")
    private CentralWarehouse centralWarehouse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_warehouse_id", referencedColumnName = "stateWarehouseId", nullable = true)
    private StateWarehouse stateWarehouse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", referencedColumnName = "storeId", nullable = true)
    private Store store;

}
