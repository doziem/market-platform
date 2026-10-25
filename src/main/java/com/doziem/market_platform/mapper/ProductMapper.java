package com.doziem.market_platform.mapper;

import com.doziem.market_platform.model.Category;
import com.doziem.market_platform.model.CentralWarehouse;
import com.doziem.market_platform.model.Product;
import com.doziem.market_platform.model.ProductImage;
import com.doziem.market_platform.model.ProductVariant;
import com.doziem.market_platform.payload.request.ProductRequest;
import com.doziem.market_platform.payload.request.ProductVariantRequest;
import com.doziem.market_platform.payload.request.UpdateProduct;
import com.doziem.market_platform.payload.response.CategoryResponse;
import com.doziem.market_platform.payload.response.ProductResponse;
import com.doziem.market_platform.payload.response.ProductVariantResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;

@Component
public class ProductMapper {

    public static Product toEntity(ProductRequest dto, Category category, CentralWarehouse centralWarehouse) {
        var variants = dto.getVariants();
        var primaryVariant = variants == null || variants.isEmpty() ? null : variants.getFirst();
        if (variants != null && variants.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Product variant cannot be null");
        }
        if (primaryVariant == null && dto.getSellingPrice() == null) {
            throw new IllegalArgumentException("Selling price is required when the product has no variants");
        }
        if (primaryVariant != null && primaryVariant.getSellingPrice() == null) {
            throw new IllegalArgumentException("The first product variant must include a selling price");
        }
        boolean variantSellingPrice = hasDifferentPrices(variants, ProductVariantRequest::getSellingPrice);
        boolean variantCostPrice = hasDifferentPrices(variants, ProductVariantRequest::getCostPrice);
        boolean variantMrp = hasDifferentPrices(variants, ProductVariantRequest::getMrp);
        double sellingPrice = primaryVariant == null
                ? dto.getSellingPrice().doubleValue()
                : primaryVariant.getSellingPrice().doubleValue();
        var costPrice = primaryVariant == null ? dto.getCostPrice() : primaryVariant.getCostPrice();
        var mrp = primaryVariant == null ? dto.getMrp() : primaryVariant.getMrp();
        Double productUnitPrice = null;
        if (!variantCostPrice) {
            if (costPrice != null) {
                productUnitPrice = costPrice.doubleValue();
            } else if (primaryVariant == null || !variantSellingPrice) {
                productUnitPrice = sellingPrice;
            }
        }
        Product product = Product.builder()
                .productName(dto.getProductName().trim())
                .sku(primaryVariant == null
                        ? "PROD-" + UUID.randomUUID()
                        : primaryVariant.getSku().trim())
                .description(dto.getDescription().trim())
                .mrp(variantMrp || mrp == null ? null : mrp.doubleValue())
                .sellingPrice(variantSellingPrice ? null : sellingPrice)
                .unitPrice(productUnitPrice)
                .quantityInStock(dto.getQuantityInStock() == null ? 0 : dto.getQuantityInStock())
                .reorderLevel(dto.getReorderLevel() == null ? 0 : dto.getReorderLevel())
                .category(category)
                .centralWarehouse(centralWarehouse)
                .stateWarehouse(null)
                .store(null)
                .build();

        if (variants == null) {
            return product;
        }
        for (var variantRequest : variants) {
            product.addVariant(ProductVariant.builder()
                    .sku(variantRequest.getSku().trim())
                    .color(variantRequest.getColor().trim())
                    .size(variantRequest.getSize().trim())
                    .sellingPrice(variantSellingPrice ? variantRequest.getSellingPrice() : null)
                    .costPrice(variantCostPrice ? variantRequest.getCostPrice() : null)
                    .mrp(variantMrp ? variantRequest.getMrp() : null)
                    .active(true)
                    .build());
        }
        return product;
    }

    private static boolean hasDifferentPrices(List<ProductVariantRequest> variants, Function<ProductVariantRequest, BigDecimal> priceGetter) {
        if (variants == null || variants.size() < 2) {
            return false;
        }
        BigDecimal firstPrice = priceGetter.apply(variants.getFirst());
        return variants.stream()
                .skip(1)
                .anyMatch(variant -> !samePrice(firstPrice, priceGetter.apply(variant)));
    }

    private static boolean samePrice(BigDecimal left, BigDecimal right) {
        return left == null ? right == null : right != null && left.compareTo(right) == 0;
    }

    public static ProductResponse toResponse(Product product) {
        return buildResponse(product);
    }

    public static List<ProductResponse> toResponseList(List<Product> products) {
        if (products == null || products.isEmpty()) {
            return new ArrayList<>();
        }

        return products
                .stream()
                .map(ProductMapper::toResponse)
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

    private static ProductResponse buildResponse(Product product) {
        if (product == null) {
            return null;
        }

        return ProductResponse.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .description(product.getDescription())
                .sku(product.getSku())
                .sellingPrice(product.getSellingPrice())
                .unitPrice(product.getUnitPrice())
                .mrp(product.getMrp())
                .quantityInStock(product.getQuantityInStock())
                .brand(product.getBrand())
                .images(product.getImages() != null ? product.getImages().stream().map(ProductImage::getImageUrl).toList() : List.of())
                .reorderLevel(product.getReorderLevel())
                .variants(product.getVariants() == null ? List.of() : product.getVariants().stream()
                        .map(variant -> ProductVariantResponse.builder()
                                .variantId(variant.getVariantId())
                                .sku(variant.getSku())
                                .color(variant.getColor())
                                .size(variant.getSize())
                                .sellingPrice(variant.getSellingPrice())
                                .costPrice(variant.getCostPrice())
                                .mrp(variant.getMrp())
                                .active(variant.getActive())
                                .build())
                        .toList())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .category(product.getCategory() != null
                        ? CategoryResponse.builder()
                                .categoryId(product.getCategory().getCategoryId())
                                .categoryName(product.getCategory().getCategoryName())
                                .build()
                        : null)
                .centralWarehouse(CentralWarehouseMapper.toResponse(product.getCentralWarehouse()))
                .stateWarehouse(StateWarehouseMapper.toResponse(product.getStateWarehouse()))
                .store(StoreMapper.storeResponse(product.getStore()))
                .build();
    }
}
