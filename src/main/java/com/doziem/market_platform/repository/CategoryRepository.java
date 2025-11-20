package com.doziem.market_platform.repository;

import com.doziem.market_platform.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository  extends JpaRepository<Category, String> {
}
