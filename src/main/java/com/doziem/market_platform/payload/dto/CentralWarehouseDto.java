package com.doziem.market_platform.payload.dto;

import com.doziem.market_platform.model.Store;
import com.doziem.market_platform.payload.request.StoreRequest;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CentralWarehouseDto {
    private String  centralWarehouseId;
    private String name;
    private String address;
    private String city;
    private String state;
    private String country;

    private StoreRequest storeRequest;;

}
