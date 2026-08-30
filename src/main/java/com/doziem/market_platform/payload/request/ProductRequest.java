package com.doziem.market_platform.payload.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
    @NotNull
    @Size(min = 4, message = "Product must have at least 4 images")
    private List<MultipartFile> images;
    private Double unitPrice;
    private Integer quantityInStock;
    private Integer reorderLevel;

    private String categoryId;
    private String centralWarehouseId;
}
