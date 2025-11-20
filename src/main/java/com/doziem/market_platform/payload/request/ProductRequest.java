package com.doziem.market_platform.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    private String productName;
    private String sku;
    private String description;
    private Double mrp;
    private Double sellingPrice;
    private String brand;
    private String image;
    private Double unitPrice;
    private Integer quantityInStock;
    private Integer reorderLevel;

    private String categoryId;
    private String centralWarehouseId;
}
