package com.doziem.market_platform.payload.response;

import com.doziem.market_platform.payload.response.StoreResponse;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@Builder
public class ProductResponse {

    private String productId;

    private String productName;
    private String sku;
    private String description;

    private Double mrp;
    private Double sellingPrice;
    private String brand;
    List<String> images;
    private double unitPrice;
    private int quantityInStock;
    private int reorderLevel;

    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    private CategoryResponse category;
    private CentralWarehouseResponse centralWarehouse;
    private StateWarehouseResponse stateWarehouse;
    private StoreResponse store;
}
