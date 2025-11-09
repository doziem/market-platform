package com.doziem.market_platform.payload.response;

import com.doziem.market_platform.enums.EmploymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StaffResponse {

    private String staffId;
    private String firstName;
    private String lastName;
    private String position;
    private String rank;
    private String roleTitle;
    private ZonedDateTime hireDate;
    private EmploymentStatus status;
    private boolean active;

    // Related entity info (for display)
    private String departmentId;
    private String departmentName;
    private String branchId;
    private String branchName;
    private String storeName;

    private String userEmail;
    private String username;
}
