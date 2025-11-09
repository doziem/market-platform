package com.doziem.market_platform.repository;

import com.doziem.market_platform.model.CentralWarehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CentralWarehouseRepository extends JpaRepository<CentralWarehouse, String > {
  CentralWarehouse findByStore_StoreId(String storeId);
}
