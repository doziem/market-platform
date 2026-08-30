package com.doziem.market_platform.repository;

import com.doziem.market_platform.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String > {
}
