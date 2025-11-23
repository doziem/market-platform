package com.doziem.market_platform.payload.response;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LogisticsCompanyResponse {

    private String logisticsId;
    private String name;
    private String contactPerson;
    private String phoneNumber;
    private String email;
    private String address;
    private String regionCovered;
    private String logisticLogoUrl;
    private String publicId;

}
