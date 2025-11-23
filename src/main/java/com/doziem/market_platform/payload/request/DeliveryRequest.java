package com.doziem.market_platform.payload.request;

import com.doziem.market_platform.enums.DeliveryStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Builder
public class DeliveryRequest {

    private String deliveryId;

    @NotBlank(message = "Delivery Status is required")
    private DeliveryStatus status;
    @NotBlank(message = "Dispatch date is required")
    private ZonedDateTime dispatchDate;
    @NotBlank(message = "Estimated Date of arrival is required")
    private ZonedDateTime estimatedArrivalDate;
    @NotBlank(message = "Delivery Date is required")
    private ZonedDateTime deliveredDate;

    @NotBlank(message = "Tracking number is required")
    private String trackingNumber;
    @NotBlank(message = "Driver's name is required")
    private String driverName;
    @NotBlank(message = "Vehicle plate number is required")
    private String vehicleNumber;

    private String centralWarehouseId;

    private String  branchId;

    private String  logisticsId;

}
