package com.doziem.market_platform.payload.response;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@Builder
public class ProductResponse {
    private String productId;
    private String productName;
    private String description;
    private String brand;
    private String sku;
    private Double sellingPrice;
    private Double unitPrice;
    private Double mrp;
    private List<ProductVariantResponse> variants;

    private List<String> images;
    private int quantityInStock;
    private int reorderLevel;

    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    private CategoryResponse category;
    private CentralWarehouseResponse centralWarehouse;
    private StateWarehouseResponse stateWarehouse;
    private StoreResponse store;
}
