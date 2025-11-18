package com.doziem.market_platform.mapper;

import com.doziem.market_platform.model.CentralWarehouse;
import com.doziem.market_platform.model.StateWarehouse;
import com.doziem.market_platform.model.StoreBranch;
import com.doziem.market_platform.payload.dto.StateWarehouseDto;

import com.doziem.market_platform.payload.response.StateWarehouseResponse;
import org.springframework.stereotype.Component;

@Component
public class StateWarehouseMapper {
    public StateWarehouse toEntity(StateWarehouseDto dto) {
        return StateWarehouse.builder()
                .name(dto.getName())
                .address(dto.getAddress())
                .city(dto.getCity())
                .state(dto.getState())
                .mainBranch(dto.getMainBranchId() != null ? StoreBranch.builder()
                        .branchId(dto.getMainBranchId()).build() : null)
                .centralWarehouse(dto.getCentralWarehouseId() != null ? CentralWarehouse.builder()
                        .centralWarehouseId(dto.getCentralWarehouseId()).build() : null)
                .build();
    }

    public static StateWarehouseResponse toResponse(StateWarehouse warehouse){
        return StateWarehouseResponse.builder()
                .stateWarehouseId(warehouse.getStateWarehouseId())
                .name(warehouse.getName())
                .address(warehouse.getAddress())
                .city(warehouse.getCity())
                .state(warehouse.getState())
                .staffList(StaffMapper.toResponseList(warehouse.getStaffList()))
                .products(ProductMapper.toResponseList(warehouse.getProducts()))
                // Additional mappings for staffList, products, mainBranch, and centralWarehouse can be added here
                .build();
    }
}
