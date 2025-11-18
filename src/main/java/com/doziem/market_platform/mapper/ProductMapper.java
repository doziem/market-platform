package com.doziem.market_platform.mapper;

import com.doziem.market_platform.model.Product;
import com.doziem.market_platform.payload.response.ProductResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductMapper {


    public static ProductResponse toResponse(Product product){
        return ProductResponse.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .description(product.getDescription())
                .sellingPrice(product.getSellingPrice())
                .quantityInStock(product.getQuantityInStock())
                .mrp(product.getMrp())
                .sku(product.getSku())
                .brand(product.getBrand())
                .image(product.getImage())
                .unitPrice(product.getUnitPrice())
                .reorderLevel(product.getReorderLevel())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .category(CategoryMapper.toResponse(product.getCategory()))
                .centralWarehouse(CentralWarehouseMapper.toResponse(product.getCentralWarehouse()))
                .stateWarehouse(StateWarehouseMapper.toResponse(product.getStateWarehouse()))
                .store(StoreMapper.storeResponse(product.getStore()))
                .build();
        
    }

    public static List<ProductResponse> toResponseList(List<Product> products) {
        if (products == null || products.isEmpty()) {
            return new ArrayList<>();
        }
        return products.stream()
                .map(ProductMapper::toResponse)
                .toList();
    }
}
