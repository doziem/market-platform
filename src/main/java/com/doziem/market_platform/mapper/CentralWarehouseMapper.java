package com.doziem.market_platform.mapper;

import com.doziem.market_platform.model.CentralWarehouse;
import com.doziem.market_platform.model.Store;
import com.doziem.market_platform.payload.dto.CentralWarehouseDto;
import com.doziem.market_platform.payload.response.CentralWarehouseResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CentralWarehouseMapper {


    public static CentralWarehouseResponse toResponse(CentralWarehouse centralWarehouse) {
        return CentralWarehouseResponse.builder()
                .centralWarehouseId(centralWarehouse.getCentralWarehouseId())
                .warehouseName(centralWarehouse.getWarehouseName())
                .address(centralWarehouse.getAddress())
                .city(centralWarehouse.getCity())
                .state(centralWarehouse.getState())
                .country(centralWarehouse.getCountry())
                .storeResponse(StoreMapper.storeResponse(centralWarehouse.getStore()))
                .build();
    }

    public static CentralWarehouse toEntity(CentralWarehouseDto dto, Store store) {
        return CentralWarehouse.builder()
                .warehouseName(dto.getWarehouseName())
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
                .warehouseName(warehouse.getWarehouseName())
                .address(warehouse.getAddress())
                .city(warehouse.getCity())
                .state(warehouse.getState())
                .storeId(warehouse.getStore() != null ? warehouse.getStore().getStoreId() : null)
                .country(warehouse.getCountry())
                .build();
    }
    public static CentralWarehouse updateEntity(CentralWarehouse warehouse, CentralWarehouseDto dto) {
        if (warehouse == null || dto == null) {
            return warehouse;
        }
        warehouse.setWarehouseName(dto.getWarehouseName());
        warehouse.setAddress(dto.getAddress());
        warehouse.setCity(dto.getCity());
        warehouse.setState(dto.getState());
        warehouse.setCountry(dto.getCountry());
        return warehouse;
    }
    public static List<CentralWarehouseResponse> toResponseList(List<CentralWarehouse> warehouses) {
        return warehouses.stream()
                .map(CentralWarehouseMapper::toResponse)
                .toList();
    }
}
