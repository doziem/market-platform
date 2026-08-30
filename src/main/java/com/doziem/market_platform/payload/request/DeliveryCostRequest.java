package com.doziem.market_platform.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record DeliveryCostRequest(
        @NotNull Double fuelCost,
        @NotNull Double laborCost,
        @NotNull Double tollFees,
        @NotNull Double otherExpenses,
        @NotBlank String deliveryId
) {
}
