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
    private ZonedDateTime hireDate;
    private String rank;
    private String roleTitle;
    private EmploymentStatus status;
    private boolean active;
    private String userId;
    private String userEmail;
    private String departmentId;
    private String departmentName;
    private String branchId;
    private String branchName;
}
