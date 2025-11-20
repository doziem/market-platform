package com.doziem.market_platform.mapper;

import com.doziem.market_platform.model.StateReplenishmentItem;
import com.doziem.market_platform.payload.dto.StateReplenishmentItemCreate;
import com.doziem.market_platform.payload.response.StateReplenishmentItemResponse;

import java.util.List;
import java.util.stream.Collectors;

public class StateReplenishmentItemMapper {

    public static StateReplenishmentItem toEntity(StateReplenishmentItemCreate create) {
        return StateReplenishmentItem.builder()
                .product(create.getProduct())
                .stateRequest(create.getRequest())
                .requestedQuantity(create.getDto().getRequestedQuantity())
                .approvedQuantity(0)
                .build();
    }

   public static StateReplenishmentItemResponse toResponse(StateReplenishmentItem item) {
        return StateReplenishmentItemResponse.builder()
                .itemId(item.getItemId())
                .productId(item.getProduct().getProductId())
                .productName(item.getProduct().getProductName())
                .requestedQuantity(item.getRequestedQuantity())
                .approvedQuantity(item.getApprovedQuantity())
                .build();
    }


    public static List<StateReplenishmentItemResponse> toResponseList(
            List<StateReplenishmentItem> items
    ) {
        return items.stream()
                .map(StateReplenishmentItemMapper::toResponse)
                .collect(Collectors.toList());
    }
    public static List<StateReplenishmentItem> toEntityList(List<StateReplenishmentItemCreate> creates) {
        return creates.stream()
                .map(StateReplenishmentItemMapper::toEntity)
                .collect(Collectors.toList());
    }

}
