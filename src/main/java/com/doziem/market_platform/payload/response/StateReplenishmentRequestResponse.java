package com.doziem.market_platform.payload.response;

import com.doziem.market_platform.enums.RequestStatus;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@Builder
public class StateReplenishmentRequestResponse {

    private String requestId;
    private String stateWarehouseId;
    private String stateWarehouseName;

    private RequestStatus status;
    private String requestedBy;
    private String approvedBy;
    private String approvedByPosition;
    private ZonedDateTime requestDate;
    private ZonedDateTime approvedDate;
    private ZonedDateTime fulfilledDate;

    private List<StateReplenishmentItemResponse> items;
}
