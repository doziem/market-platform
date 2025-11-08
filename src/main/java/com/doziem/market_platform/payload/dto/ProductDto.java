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

    private String  productId;

    private String productName;

    private String sku;

    private String description;

    private Double mrp;

    private Double sellingPrice;

    private String brand;

    private String image;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

    private Category category;

    private StoreRequest store;
}
