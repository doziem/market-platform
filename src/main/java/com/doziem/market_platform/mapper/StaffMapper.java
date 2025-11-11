package com.doziem.market_platform.mapper;

import com.doziem.market_platform.model.StoreBranch;
import com.doziem.market_platform.model.User;
import com.doziem.market_platform.model.staff.Department;
import com.doziem.market_platform.model.staff.Staff;
import com.doziem.market_platform.payload.dto.StaffDto;
import com.doziem.market_platform.payload.request.StaffRequest;
import com.doziem.market_platform.payload.response.StaffResponse;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StaffMapper {

    /**
     * Convert Staff entity to StaffResponse DTO using builder
     */
    public static StaffResponse toResponse(Staff staff) {

        if (staff == null) return null;

        return StaffResponse.builder()
                .staffId(staff.getStaffId())
                .firstName(staff.getFirstName())
                .lastName(staff.getLastName())
                .position(staff.getPosition())
                .hireDate(staff.getHireDate())
                .rank(staff.getRank())
                .roleTitle(staff.getRoleTitle())
                .status(staff.getStatus())
                .active(staff.isActive())
                .userId(staff.getUserAccount() != null ? staff.getUserAccount().getUserId() : null)
                .userEmail(staff.getUserAccount() != null ? staff.getUserAccount().getEmail() : null)
                .departmentId(staff.getDepartment() != null ? staff.getDepartment().getDepartmentId() : null)
                .departmentName(staff.getDepartment() != null ? staff.getDepartment().getDepartmentName() : null)
                .branchId(staff.getBranch() != null ? staff.getBranch().getBranchId() : null)
                .branchName(staff.getBranch() != null ? staff.getBranch().getBranchName() : null)
                .build();

    }

    /**
     * Convert StaffRequest DTO to Staff entity
     * Associations like User, Department, Branch are set in the service layer
     */
    public static Staff toEntity(StaffRequest request) {
        if (request == null) return null;

        return Staff.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .position(request.getPosition())
                .hireDate(request.getHireDate())
                .rank(request.getRank())
                .roleTitle(request.getRoleTitle())
                .status(request.getStatus())
                .active(request.isActive())
                .build();
    }


    /**
     * Update existing Staff entity from StaffRequest
     */
    public static void updateEntity(Staff staff, StaffRequest request) {
        if (staff == null || request == null) return;

        if (request.getFirstName() != null) staff.setFirstName(request.getFirstName());
        if (request.getLastName() != null) staff.setLastName(request.getLastName());
        if (request.getPosition() != null) staff.setPosition(request.getPosition());
        if (request.getHireDate() != null) staff.setHireDate(request.getHireDate());
        if (request.getRank() != null) staff.setRank(request.getRank());
        if (request.getRoleTitle() != null) staff.setRoleTitle(request.getRoleTitle());
        if (request.getStatus() != null) staff.setStatus(request.getStatus());
        staff.setActive(request.isActive());
    }

    /**
     * Convert a list of Staff entities to a list of StaffResponse DTOs
     */
    public static List<StaffResponse> toResponseList(List<Staff> staffList) {
        return staffList == null ? List.of() :
                staffList.stream().map(StaffMapper::toResponse).collect(Collectors.toList());
    }
}
