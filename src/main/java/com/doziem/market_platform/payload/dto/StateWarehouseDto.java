package com.doziem.market_platform.payload.dto;

import com.doziem.market_platform.model.CentralWarehouse;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class StateWarehouseDto {
    private String name;
    private String address;
    private String city;
    private String state;

    private String mainBranchId;
    private String centralWarehouseId;
}
