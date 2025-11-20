package com.doziem.market_platform.service.warehouse;

import com.doziem.market_platform.payload.dto.StateReplenishmentRequestDto;
import com.doziem.market_platform.payload.response.StateReplenishmentRequestResponse;

import java.util.List;

public interface StateReplenishmentRequestService {

    StateReplenishmentRequestResponse createRequest(StateReplenishmentRequestDto dto);

    StateReplenishmentRequestResponse getRequestById(String requestId);

    List<StateReplenishmentRequestResponse> getAllRequests();

    StateReplenishmentRequestResponse approveRequest(String requestId, String approvedBy);

    StateReplenishmentRequestResponse rejectRequest(String requestId, String approvedBy);
}
