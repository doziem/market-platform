package com.doziem.market_platform.payload.dto;

import com.doziem.market_platform.model.Product;
import com.doziem.market_platform.model.StateReplenishmentRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StateReplenishmentItemCreate {

    private StateReplenishmentItemDto dto;
    private Product product;
    private StateReplenishmentRequest request;
}
