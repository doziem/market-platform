package com.doziem.market_platform.payload.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StateReplenishmentItemDto {

    @NotBlank(message = "Product ID is required")
    private String productId;

    private int requestedQuantity;
}
