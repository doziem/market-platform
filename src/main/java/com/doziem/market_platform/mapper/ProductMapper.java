package com.doziem.market_platform.mapper;

import com.doziem.market_platform.model.Category;
import com.doziem.market_platform.model.CentralWarehouse;
import com.doziem.market_platform.model.Product;
import com.doziem.market_platform.model.ProductImage;
import com.doziem.market_platform.model.Store;
import com.doziem.market_platform.payload.request.ProductRequest;
import com.doziem.market_platform.payload.request.UpdateProduct;
import com.doziem.market_platform.payload.response.ProductResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductMapper {

    public static Product toEntity(ProductRequest dto, Category category, CentralWarehouse centralWarehouse) {
        return Product.builder()
                .productName(dto.getProductName())
                .sku(dto.getSku())
                .description(dto.getDescription())
                .mrp(dto.getMrp())
                .sellingPrice(dto.getSellingPrice())
                .unitPrice(dto.getUnitPrice())
                .quantityInStock(dto.getQuantityInStock())
                .reorderLevel(dto.getReorderLevel())
                .category(category)
                .centralWarehouse(centralWarehouse)
                .stateWarehouse(null)
                .store(null)
                .build();
    }

    public static ProductResponse toResponse(Product product) {
        return buildResponse(product, true);
    }

    public static List<ProductResponse> toResponseList(List<Product> products) {
        if (products == null || products.isEmpty()) {
            return new ArrayList<>();
        }

        return products.stream()
                .map(product -> buildResponse(product, false))
                .toList();
    }

    public static List<ProductResponse> toDetailedResponseList(List<Product> products) {
        if (products == null || products.isEmpty()) {
            return new ArrayList<>();
        }

        return products.stream()
                .map(ProductMapper::toResponse)
                .toList();
    }

    public static Product toUpdateEntity(Product existingProduct, UpdateProduct product) {
        existingProduct.setProductName(product.getProductName() != null ? product.getProductName() : existingProduct.getProductName());
        existingProduct.setMrp(product.getMrp() != null ? product.getMrp() : existingProduct.getMrp());
        existingProduct.setBrand(product.getBrand() != null ? product.getBrand() : existingProduct.getBrand());
        existingProduct.setSellingPrice(product.getSellingPrice() != null ? product.getSellingPrice() : existingProduct.getSellingPrice());
        existingProduct.setDescription(product.getDescription() != null ? product.getDescription() : existingProduct.getDescription());
        existingProduct.setSku(product.getSku() != null ? product.getSku() : existingProduct.getSku());
        existingProduct.setUnitPrice(product.getUnitPrice() != null ? product.getUnitPrice() : existingProduct.getUnitPrice());
        existingProduct.setQuantityInStock(product.getQuantityInStock() != null ? product.getQuantityInStock() : existingProduct.getQuantityInStock());
        existingProduct.setReorderLevel(product.getReorderLevel() != null ? product.getReorderLevel() : existingProduct.getReorderLevel());
        existingProduct.setCategory(product.getCategory() != null ? product.getCategory() : existingProduct.getCategory());
        existingProduct.setCentralWarehouse(product.getCentralWarehouse() != null ? product.getCentralWarehouse() : existingProduct.getCentralWarehouse());
        existingProduct.setStore(product.getStore() != null ? product.getStore() : existingProduct.getStore());
        existingProduct.setStateWarehouse(product.getStateWarehouse() != null ? product.getStateWarehouse() : existingProduct.getStateWarehouse());
        return existingProduct;
    }

    private static ProductResponse buildResponse(Product product, boolean includeRelations) {
        if (product == null) {
            return null;
        }

        return ProductResponse.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .description(product.getDescription())
                .sellingPrice(product.getSellingPrice())
                .quantityInStock(product.getQuantityInStock())
                .mrp(product.getMrp())
                .sku(product.getSku())
                .brand(product.getBrand())
                .images(product.getImages() != null ? product.getImages().stream().map(ProductImage::getImageUrl).toList() : List.of())
                .unitPrice(product.getUnitPrice())
                .reorderLevel(product.getReorderLevel())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .category(includeRelations ? CategoryMapper.toResponse(product.getCategory()) : null)
                .centralWarehouse(includeRelations ? CentralWarehouseMapper.toResponse(product.getCentralWarehouse()) : null)
                .stateWarehouse(includeRelations ? StateWarehouseMapper.toResponse(product.getStateWarehouse()) : null)
                .store(includeRelations ? StoreMapper.storeResponse(product.getStore()) : null)
                .build();
    }
}
