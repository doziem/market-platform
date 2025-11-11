package com.doziem.market_platform.payload.dto;

import com.doziem.market_platform.model.StoreBranch;
import com.doziem.market_platform.model.staff.Staff;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class HumanResourceDto {

    private String  humanResourceId;

    @NotBlank(message = "Staff information is required")
    private Staff staff;

    @NotBlank(message = "At least one department is required")
    private List<DepartmentDto> departments;

    @NotBlank(message = "Store branch is required")
    private StoreBranch branch;

    @NotBlank(message = "Job title is required")
    private String roleTitle ;

    private boolean active;
}
