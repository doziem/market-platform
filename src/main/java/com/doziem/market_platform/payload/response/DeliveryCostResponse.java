package com.doziem.market_platform.payload.response;

import lombok.Builder;

@Builder
public record DeliveryCostResponse(
        String deliveryCostId,
        double fuelCost,
        double laborCost,
        double tollFees,
        double otherExpenses,
        double totalCost,
        String deliveryId,
        String deliveryType,
        String logisticsCompanyId
) {
}
