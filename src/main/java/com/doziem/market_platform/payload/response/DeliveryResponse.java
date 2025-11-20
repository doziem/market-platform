package com.doziem.market_platform.payload.response;

import com.doziem.market_platform.enums.DeliveryStatus;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Builder
public class DeliveryResponse {

    private String deliveryId;

    private DeliveryStatus status;
    private ZonedDateTime dispatchDate;
    private ZonedDateTime estimatedArrivalDate;
    private ZonedDateTime deliveredDate;

    private String trackingNumber;
    private String driverName;
    private String vehicleNumber;

    private String centralWarehouseId;

    private String  branchId;

    private LogisticsCompanyResponse logisticsCompanyResponse;
}
