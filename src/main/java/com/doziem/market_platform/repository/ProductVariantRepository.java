package com.doziem.market_platform.repository;

import com.doziem.market_platform.model.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, String> {

    List<ProductVariant> findByProduct_ProductId(String productId);

    Optional<ProductVariant> findBySku(String sku);

    boolean existsBySku(String sku);

    boolean existsByProduct_ProductIdAndColorIgnoreCaseAndSizeIgnoreCase(
            String productId,
            String color,
            String size
    );

}

