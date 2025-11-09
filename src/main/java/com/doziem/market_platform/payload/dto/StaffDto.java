package com.doziem.market_platform.payload.dto;

import com.doziem.market_platform.enums.EmploymentStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Builder
public class StaffDto {

    private String staffId;

    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @NotBlank(message = "Position cannot be blank")
    private String position;

    private ZonedDateTime hireDate;

    private String rank;

    @Size(min = 2, message = "Job title must have at least 2 characters")
    private String roleTitle;

    @NotNull(message = "User ID is required")
    private String userId;

    @NotNull(message = "Department ID is required")
    private String departmentId;

    @NotNull(message = "Branch ID is required")
    private String branchId;

    @NotNull(message = "Employment status is required")
    private EmploymentStatus status;

    private boolean active;
}
