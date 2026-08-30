package com.doziem.market_platform.payload.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class StateWarehouseResponse {
    private String stateWarehouseId;
    private String name;
    private String address;
    private String city;
    private String state;

    private List<StaffResponse> staffList;
    private List<ProductResponse> products;

    private StoreBranchResponse mainBranch;
    private CentralWarehouseResponse centralWarehouse;
}
