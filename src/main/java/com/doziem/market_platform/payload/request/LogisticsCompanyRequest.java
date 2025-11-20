package com.doziem.market_platform.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LogisticsCompanyRequest {
    private String  logisticsId;

    @NotBlank(message = "Logistics Company name is required")
    private String name;
    @NotBlank(message = "Contact Person is required")
    private String contactPerson;
    @NotBlank(message = "Phone Number is required")
    private String phoneNumber;
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Address is required")
    private String address;
    @NotBlank(message = "Region Covered is required")
    private String regionCovered;

}
