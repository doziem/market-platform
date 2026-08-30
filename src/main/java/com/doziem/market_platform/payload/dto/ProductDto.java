package com.doziem.market_platform.payload.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDto {

    @NotBlank(message = "Product name is required")
    private String productName;
    @NotBlank(message = "SKU is required")
    private String sku;
    @NotBlank(message = "Description is required")
    private String description;

    private Double mrp;
    @NotBlank(message = "Selling price is required")
    private Double sellingPrice;
    @NotBlank(message = "Brand Name is required")
    private String brand;
    @NotBlank(message = "Image is required")
    private String image;
    @NotBlank(message = "Unit price is required")
    private double unitPrice;
    @NotBlank(message = "Quantity in stock is required")
    private int quantityInStock;
    @NotBlank(message = "Reorder level is required")
    private int reorderLevel;

    @NotBlank(message = "Category ID is required")
    private String categoryId;
    @NotBlank(message = "Central Warehouse ID is required")
    private String centralWarehouseId;
    @NotBlank(message = "State Warehouse ID is required")
    private String stateWarehouseId;
    @NotBlank(message = "Store Branch ID is required")
    private String storeBranchId;
}
