package com.doziem.market_platform.mapper;

import com.doziem.market_platform.model.Product;
import com.doziem.market_platform.model.StateReplenishmentItem;
import com.doziem.market_platform.model.StateReplenishmentRequest;
import com.doziem.market_platform.model.StateWarehouse;
import com.doziem.market_platform.payload.dto.StateReplenishmentItemCreate;
import com.doziem.market_platform.payload.dto.StateReplenishmentRequestDto;
import com.doziem.market_platform.payload.response.StateReplenishmentItemResponse;
import com.doziem.market_platform.payload.response.StateReplenishmentRequestResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StateReplenishmentMapper {

    public static StateReplenishmentRequest toEntity(StateReplenishmentRequestDto dto) {
        // Build the request without items
        StateReplenishmentRequest request = StateReplenishmentRequest.builder()
                .stateWarehouse(
                        StateWarehouse.builder()
                                .stateWarehouseId(dto.getStateWarehouseId())
                                .build()
                )
                .status(dto.getStatus())
                .requestedBy(dto.getRequestedBy())
                .requestDate(dto.getRequestDate())
                .approvedDate(null)      // approval happens later
                .fulfilledDate(null)     // fulfillment happens later
                .items(new ArrayList<>()) // items added after building
                .build();
        // Convert items after the request is already created
        List<StateReplenishmentItem> items = dto.getItems().stream()
                .map(itemDto -> {

                    // Build wrapper
                    StateReplenishmentItemCreate wrapper = StateReplenishmentItemCreate.builder()
                            .dto(itemDto).product(Product.builder()
                                            .productId(itemDto.getProductId())
                                            .build()
                            )
                            .request(request)
                            .build();

                    // Convert wrapper → Entity
                    return StateReplenishmentItemMapper.toEntity(wrapper);
                })
                .collect(Collectors.toList());

        //Set items into parent (to maintain bidirectional consistency)
        request.setItems(items);

        return request;
    }


    public static StateReplenishmentRequestResponse toResponse(StateReplenishmentRequest request) {
        return StateReplenishmentRequestResponse.builder()
                .requestId(request.getRequestId())
                .stateWarehouseId(request.getStateWarehouse().getStateWarehouseId())
                .stateWarehouseName(request.getStateWarehouse().getName())
                .status(request.getStatus())
                .requestedBy(request.getRequestedBy())
                .approvedBy(request.getApprovedBy())
                .approvedByPosition(request.getApprovedByPosition())
                .requestDate(request.getRequestDate())
                .approvedDate(request.getApprovedDate())
                .fulfilledDate(request.getFulfilledDate())
                .items(StateReplenishmentItemMapper.toResponseList(request.getItems()))
                .build();
    }


}
