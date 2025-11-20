package com.doziem.market_platform.payload.dto;

import com.doziem.market_platform.enums.RequestStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@Builder
public class StateReplenishmentRequestDto {

    @NotBlank(message = "Warehouse ID is required")
    private String stateWarehouseId;

    @NotNull(message = "Request status is required")
    private RequestStatus status;

    @NotBlank(message = "Requested by is required")
    private String requestedBy;

    private ZonedDateTime requestDate;

    private ZonedDateTime neededByDate;

    private List<StateReplenishmentItemDto> items;
}
