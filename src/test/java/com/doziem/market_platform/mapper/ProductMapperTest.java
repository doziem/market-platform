package com.doziem.market_platform.mapper;

import com.doziem.market_platform.payload.request.ProductRequest;
import com.doziem.market_platform.payload.request.ProductVariantRequest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ProductMapperTest {

    @Test
    void storesOneProductPriceWhenVariantPricesAreTheSame() {
        var product = ProductMapper.toEntity(request(
                variant("SKU-1", "Blue", "S", "100.00", "50.00", "120.00"),
                variant("SKU-2", "Red", "M", "100.0", "50.0", "120.0")
        ), null, null);

        assertEquals(100.0, product.getSellingPrice(), 0.001);
        assertEquals(50.0, product.getUnitPrice(), 0.001);
        assertEquals(120.0, product.getMrp(), 0.001);
        assertNull(product.getVariants().get(0).getSellingPrice());
        assertNull(product.getVariants().get(0).getCostPrice());
        assertNull(product.getVariants().get(0).getMrp());
        assertNull(product.getVariants().get(1).getSellingPrice());
    }

    @Test
    void storesVariantPricesWhenSellingPriceDiffers() {
        var product = ProductMapper.toEntity(request(
                variant("SKU-1", "Blue", "S", "100.00", "50.00", "120.00"),
                variant("SKU-2", "Red", "M", "110.00", "50.00", "120.00")
        ), null, null);

        assertNull(product.getSellingPrice());
        assertEquals(50.0, product.getUnitPrice(), 0.001);
        assertEquals(new BigDecimal("100.00"), product.getVariants().get(0).getSellingPrice());
        assertEquals(new BigDecimal("110.00"), product.getVariants().get(1).getSellingPrice());
        assertNull(product.getVariants().get(0).getCostPrice());
        assertNull(product.getVariants().get(0).getMrp());
    }

    @Test
    void storesVariantPricesWhenCostPriceDiffers() {
        var product = ProductMapper.toEntity(request(
                variant("SKU-1", "Blue", "S", "100.00", "50.00", "120.00"),
                variant("SKU-2", "Red", "M", "100.00", "55.00", "120.00")
        ), null, null);

        assertEquals(100.0, product.getSellingPrice(), 0.001);
        assertNull(product.getUnitPrice());
        assertEquals(new BigDecimal("50.00"), product.getVariants().get(0).getCostPrice());
        assertEquals(new BigDecimal("55.00"), product.getVariants().get(1).getCostPrice());
        assertNull(product.getVariants().get(0).getSellingPrice());
    }

    private ProductRequest request(ProductVariantRequest... variants) {
        return ProductRequest.builder()
                .productName("Test product")
                .description("Test description")
                .brand("Test brand")
                .variants(List.of(variants))
                .build();
    }

    private ProductVariantRequest variant(
            String sku, String color, String size, String sellingPrice, String costPrice, String mrp) {
        return ProductVariantRequest.builder()
                .sku(sku)
                .color(color)
                .size(size)
                .sellingPrice(new BigDecimal(sellingPrice))
                .costPrice(new BigDecimal(costPrice))
                .mrp(new BigDecimal(mrp))
                .build();
    }
}
