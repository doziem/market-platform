package com.doziem.market_platform.repository;

import com.doziem.market_platform.model.DeliveryItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryItemRepository extends JpaRepository<DeliveryItem, String > {
}
