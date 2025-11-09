package com.doziem.market_platform.service;

import com.doziem.market_platform.payload.dto.CentralWarehouseDto;
import com.doziem.market_platform.system.Result;

public interface CentralWarehouseService {
    Result createCentralWarehouse(String storeId, CentralWarehouseDto dto);
    Result getCentralWarehouseByStoreId(String  storeId);
    Result updateCentralWarehouse(String centralWarehouseId, CentralWarehouseDto dto);
    Result getCentralWarehouseById(String centralWarehouseId);
    Result deleteCentralWarehouse(String centralWarehouseId);

}
