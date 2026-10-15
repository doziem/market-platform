package com.doziem.market_platform.payload.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariantRequest {

    @NotBlank(message = "SKU is required")
    @Size(max = 100)
    private String sku;

    @NotBlank(message = "Color is required")
    @Size(max = 50)
    private String color;

    @NotBlank(message = "Size is required")
    @Size(max = 50)
    private String size;

    @NotNull(message = "Selling price is required")
    @DecimalMin(value = "0.01",
            message = "Selling price must be greater than zero")
    private BigDecimal sellingPrice;

    @DecimalMin(value = "0.00")
    private BigDecimal costPrice;

    @DecimalMin(value = "0.00")
    private BigDecimal mrp;
}
