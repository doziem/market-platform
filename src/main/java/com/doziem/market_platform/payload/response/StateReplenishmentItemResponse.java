package com.doziem.market_platform.payload.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StateReplenishmentItemResponse {

    private String itemId;
    private String productId;
    private String productName;
    private int requestedQuantity;
    private int approvedQuantity;
}
