package com.doziem.market_platform.payload.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CentralWarehouseDto {

    @NotBlank(message = "warehouseName is required")
    @Size(max = 100, message = "warehouseName must be at most 100 characters")
    private String warehouseName;

    @NotBlank(message = "address is required")
    @Size(max = 255, message = "address must be at most 255 characters")
    private String address;

    @NotBlank(message = "city is required")
    @Size(max = 100, message = "city must be at most 100 characters")
    private String city;

    @NotBlank(message = "state is required")
    @Size(max = 100, message = "state must be at most 100 characters")
    private String state;

    @NotBlank(message = "country is required")
    @Size(max = 100, message = "country must be at most 100 characters")
    private String country;

    @NotBlank(message = "storeId is required")
    private String storeId;
}
