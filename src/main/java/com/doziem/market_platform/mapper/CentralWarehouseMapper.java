package com.doziem.market_platform.mapper;

import com.doziem.market_platform.model.CentralWarehouse;
import com.doziem.market_platform.model.Store;
import com.doziem.market_platform.model.StoreBranch;
import com.doziem.market_platform.payload.dto.CentralWarehouseDto;
import com.doziem.market_platform.payload.request.StoreBranchRequest;
import org.springframework.stereotype.Component;

@Component
public class CentralWarehouseMapper {

    public CentralWarehouse toEntity(CentralWarehouseDto dto, Store store) {
        return CentralWarehouse.builder()
                .name(dto.getName())
                .address(dto.getAddress())
                .city(dto.getCity())
                .state(dto.getState())
                .country(dto.getCountry())
                .store(store)
                .build();
    }

    public static CentralWarehouseDto toDto(CentralWarehouse warehouse) {
        if (warehouse == null) {
            return null;
        }
        return CentralWarehouseDto.builder()
                .centralWarehouseId(warehouse.getCentralWarehouseId())
                .name(warehouse.getName())
                .address(warehouse.getAddress())
                .city(warehouse.getCity())
                .state(warehouse.getState())
                .storeRequest(StoreMapper.toDto(warehouse.getStore()))
                .country(warehouse.getCountry())
                .build();
    }
}
