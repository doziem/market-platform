package com.doziem.market_platform.payload.dto;

import com.doziem.market_platform.payload.response.StaffResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DepartmentDto {

    private String departmentId;


    @NotBlank(message = "Department name cannot be empty")
    private String departmentName;

    @NotNull(message = "Human Resource Id is required")
    private String humanResourceId;

    @NotNull(message = "Branch Id is required")
    private String branchId;

    private List<StaffResponse> staff;

    private String description;
}
