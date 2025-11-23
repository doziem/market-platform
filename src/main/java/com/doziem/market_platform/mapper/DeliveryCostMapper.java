package com.doziem.market_platform.mapper;

import com.doziem.market_platform.model.BaseDelivery;
import com.doziem.market_platform.model.DeliveryCost;
import com.doziem.market_platform.payload.request.DeliveryCostRequest;
import com.doziem.market_platform.payload.response.DeliveryCostResponse;
import org.springframework.stereotype.Component;

@Component
public class DeliveryCostMapper {

    public DeliveryCost toEntity(DeliveryCostRequest req, BaseDelivery delivery) {
        double total = req.fuelCost()
                + req.laborCost()
                + req.tollFees()
                + req.otherExpenses();

        return DeliveryCost.builder()
                .delivery(delivery)
                .fuelCost(req.fuelCost())
                .laborCost(req.laborCost())
                .tollFees(req.tollFees())
                .otherExpenses(req.otherExpenses())
                .totalCost(total)
                .build();
    }

    public DeliveryCostResponse toResponse(DeliveryCost cost) {

        return DeliveryCostResponse.builder()
                .deliveryCostId(cost.getDeliveryCostId())
                .fuelCost(cost.getFuelCost())
                .laborCost(cost.getLaborCost())
                .tollFees(cost.getTollFees())
                .otherExpenses(cost.getOtherExpenses())
                .totalCost(cost.getTotalCost())
                .deliveryId(cost.getDelivery().getDeliveryId())
//                .deliveryType(cost.getDelivery().getDeliveryType().name())
                .logisticsCompanyId(cost.getDelivery().getLogisticsCompany().getLogisticsId())
                .build();
    }
}
