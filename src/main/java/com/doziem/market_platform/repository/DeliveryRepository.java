package com.doziem.market_platform.repository;

import com.doziem.market_platform.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, String > {
}
