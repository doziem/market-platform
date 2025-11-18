package com.doziem.market_platform.payload.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CentralWarehouseResponse {
    private String centralWarehouseId;
    private String warehouseName;
    private String address;
    private String city;
    private String state;
    private String country;

    private StoreResponse storeResponse;
}
