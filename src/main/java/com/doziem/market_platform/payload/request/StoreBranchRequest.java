package com.doziem.market_platform.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record StoreBranchRequest(
        @NotBlank(message = "Branch name is required")
        @Size(min = 2, max = 50, message = "Branch name must be between 2 and 50 characters")
        String branchName,

        @NotBlank(message = "Address is required")
        @Size(max = 255, message = "Address must not exceed 255 characters")
        String address,

        @NotBlank(message = "City is required")
        String city,

        String state,

        String country,

        @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{7,15}$",
                message = "Phone number must be valid")
        String phoneNumber,

        boolean mainBranch
) {
}

