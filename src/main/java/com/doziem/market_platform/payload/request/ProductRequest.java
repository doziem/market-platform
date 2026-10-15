package com.doziem.market_platform.payload.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    @NotBlank(message = "Product name is required")
    @Size(max = 150)
    private String productName;
    @NotBlank(message = "Description is required")
    private String description;
    @NotBlank(message = "Brand is required")
    private String brand;
    @NotNull
    @Size(min = 4, message = "Product must have at least 4 images")
    private List<MultipartFile> images;

    private Integer quantityInStock;
    private Integer reorderLevel;

    @DecimalMin(value = "0.01", message = "Selling price must be greater than zero")
    private BigDecimal sellingPrice;

    @DecimalMin(value = "0.00", message = "Cost price cannot be negative")
    private BigDecimal costPrice;

    @DecimalMin(value = "0.00", message = "MRP cannot be negative")
    private BigDecimal mrp;

    private String categoryId;
    private String storeId;
    private String centralWarehouseId;

    @Valid
    private List<ProductVariantRequest> variants;

}
