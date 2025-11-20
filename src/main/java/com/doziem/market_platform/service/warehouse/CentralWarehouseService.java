package com.doziem.market_platform.service.warehouse;

import com.doziem.market_platform.payload.dto.CentralWarehouseDto;
import com.doziem.market_platform.payload.response.CentralWarehouseResponse;
import com.doziem.market_platform.system.Result;

import java.util.List;

public interface CentralWarehouseService {

    CentralWarehouseResponse createWarehouse(CentralWarehouseDto request);

    CentralWarehouseResponse getWarehouseById(String id);

    List<CentralWarehouseResponse> getAllWarehouses();

    CentralWarehouseResponse updateWarehouse(String id, CentralWarehouseDto request);

    void deleteWarehouse(String id);

    Result getCentralWarehouseByStoreId(String  storeId);
}
