package com.doziem.market_platform.mapper;

import com.doziem.market_platform.model.*;
import com.doziem.market_platform.payload.request.ProductRequest;
import com.doziem.market_platform.payload.request.UpdateProduct;
import com.doziem.market_platform.payload.response.ProductResponse;
import com.doziem.market_platform.repository.CategoryRepository;
import com.doziem.market_platform.repository.CentralWarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductMapper {


    public static   Product toEntity(ProductRequest dto, Category category, CentralWarehouse centralWarehouse){

        return Product.builder()
                .productName(dto.getProductName())
                .sku(dto.getSku())
                .description(dto.getDescription())
                .mrp(dto.getMrp())
                .sellingPrice(dto.getSellingPrice())
                .brand(dto.getBrand())
                .image(dto.getImage())
                .unitPrice(dto.getUnitPrice())
                .quantityInStock(dto.getQuantityInStock())
                .reorderLevel(dto.getReorderLevel())
                .category(category)
                .centralWarehouse(centralWarehouse)
                .store(null)
                .stateWarehouse(null)
                .build();
    }


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

    public static Product toUpdateEntity(Product existingProduct, UpdateProduct product){
        return Product.builder()
                .productName(product.getProductName() != null? product.getProductName() : existingProduct.getProductName())
                .mrp(product.getMrp() != null ? product.getMrp() : existingProduct.getMrp())
                .brand(product.getBrand() !=null ? product.getBrand() : existingProduct.getBrand())
                .sellingPrice(product.getSellingPrice() != null ? product.getSellingPrice() : existingProduct.getSellingPrice())
                .description(product.getDescription() != null ? product.getDescription() : existingProduct.getDescription())
                .sku( product.getSku() != null ? product.getSku() : existingProduct.getSku())
                .image( product.getImage() != null ? product.getImage() : existingProduct.getImage())
                .unitPrice( product.getUnitPrice() != null ? product.getUnitPrice() : existingProduct.getUnitPrice())
                .quantityInStock( product.getQuantityInStock() != null ? product.getQuantityInStock() : existingProduct.getQuantityInStock())
                .reorderLevel( product.getReorderLevel() != null ? product.getReorderLevel() : existingProduct.getReorderLevel())
                .category( product.getCategory() != null ? product.getCategory() : existingProduct.getCategory())
                .centralWarehouse( product.getCentralWarehouse() != null ? product.getCentralWarehouse() : existingProduct.getCentralWarehouse())
                .store( product.getStore() != null ? product.getStore() : existingProduct.getStore())
                .stateWarehouse( product.getStateWarehouse() != null ? product.getStateWarehouse() : existingProduct.getStateWarehouse())
                .build();
    }
}
