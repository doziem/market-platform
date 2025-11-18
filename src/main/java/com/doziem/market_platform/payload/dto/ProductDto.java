package com.doziem.market_platform.payload.dto;

import com.doziem.market_platform.model.Category;
import com.doziem.market_platform.payload.request.StoreRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@Builder
public class ProductDto {

    private String productName;
    private String sku;
    private String description;

    private Double mrp;
    private Double sellingPrice;
    private String brand;
    private String image;
    private double unitPrice;
    private int quantityInStock;
    private int reorderLevel;

    private String categoryId;
    private String centralWarehouseId;
    private String stateWarehouseId;
    private String storeId;
}
