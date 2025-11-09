package com.doziem.market_platform.payload.request;

import com.doziem.market_platform.enums.StoreStatus;
import com.doziem.market_platform.payload.dto.UserDto;
import com.doziem.market_platform.payload.dto.WorkHourDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class StoreRequest {

    @NotBlank(message = "Store name is required")
    @Size(min = 3, max = 100)
    private String storeName;

    private String storeType;

    private StoreStatus status;

    private MultipartFile storeLogo;

    private Boolean hasLogo;

    private UserDto userDto;

    @NotBlank
    @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "Phone number must be 7–15 digits and may start with +")
    private String phoneNumber;

    @NotBlank
    private String address;

    @NotBlank
    private String city;
    @NotBlank
    private String lga;
    @NotBlank
    private String state;

    @NotBlank
    private String country;

    private String zipCode;

    private String countryCode;
    private String iso;

    private boolean isHeadQuarter ;

    private WorkHourDto weekday;
    private WorkHourDto saturday;
    private WorkHourDto sunday;

}
