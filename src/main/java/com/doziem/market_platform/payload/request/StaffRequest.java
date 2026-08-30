package com.doziem.market_platform.payload.request;

import com.doziem.market_platform.enums.EmploymentStatus;

import java.time.ZonedDateTime;

import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StaffRequest {
    private String firstName;
    private String lastName;
    private String position;
    private ZonedDateTime hireDate;
    private String rank;
    private String roleTitle;
    private String userId;
    private String departmentId;
    private String branchId;
    private EmploymentStatus status;
    private boolean active;
}
