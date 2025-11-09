package com.doziem.market_platform.payload.response;

import com.doziem.market_platform.enums.StoreStatus;
import com.doziem.market_platform.model.Product;
import com.doziem.market_platform.model.StoreBranch;
import com.doziem.market_platform.payload.dto.UserDto;
import com.doziem.market_platform.payload.dto.WorkHourDto;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class StoreResponse {

    private String storeId;
    private String storeName;

    private String storeType;

    private StoreStatus status;

    private String  storeLogo;

    private Boolean hasLogo;

    private UserDto user;

    private String phoneNumber;

    private String address;

    private String city;

    private String lga;

    private String state;

    private String country;

    private String zipCode;

    private String countryCode;
    private String iso;

    private boolean isHeadQuarter;

    private List<Product> products = new ArrayList<>();

    private List<StoreBranchResponse> storeBranches = new ArrayList<>();

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

    private WorkHourDto weekday;
    private WorkHourDto saturday;
    private WorkHourDto sunday;

}
