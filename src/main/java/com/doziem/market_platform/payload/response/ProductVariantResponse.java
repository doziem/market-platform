package com.doziem.market_platform.payload.response;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVariantResponse {

    private String variantId;
    private String sku;
    private String color;
    private String size;
    private BigDecimal sellingPrice;
    private BigDecimal costPrice;
    private BigDecimal mrp;
    private Boolean active;
}
