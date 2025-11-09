package com.doziem.market_platform.mapper;

import com.doziem.market_platform.model.StateWarehouse;
import com.doziem.market_platform.model.StoreBranch;
import com.doziem.market_platform.payload.dto.StateWarehouseDto;
import com.doziem.market_platform.payload.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class StateWarehouseMapper {

    public StateWarehouse toEntity(StateWarehouseDto stateWarehouseDto, StoreBranch storeBranch) {
        return StateWarehouse.builder()
                .name(stateWarehouseDto.getName())
                .address(stateWarehouseDto.getAddress())
                .city(stateWarehouseDto.getCity())
                .state(stateWarehouseDto.getState())
                .centralWarehouse(stateWarehouseDto.getCentralWarehouse())
                .mainBranch(storeBranch)
                .build();
    }
    public static StateWarehouseDto toDto(StateWarehouse stateWarehouse) {
        if (stateWarehouse == null) {
            return null;
        }
        return StateWarehouseDto.builder()
                .stateWarehouseId(stateWarehouse.getStateWarehouseId())
                .name(stateWarehouse.getName())
                .address(stateWarehouse.getAddress())
                .city(stateWarehouse.getCity())
                .state(stateWarehouse.getState())
                .branchManagerName(stateWarehouse.getManagerName().getDisplayName())
                .mainBranch(stateWarehouse.getMainBranch())
                .centralWarehouse(stateWarehouse.getCentralWarehouse())
                .build();
    }
}
