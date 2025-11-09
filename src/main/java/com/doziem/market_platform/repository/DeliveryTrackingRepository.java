package com.doziem.market_platform.repository;

import com.doziem.market_platform.model.DeliveryTracking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryTrackingRepository extends JpaRepository<DeliveryTracking, String > {
}
