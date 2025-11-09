package com.doziem.market_platform.mapper;

import com.doziem.market_platform.model.StoreBranch;
import com.doziem.market_platform.model.User;
import com.doziem.market_platform.model.staff.Department;
import com.doziem.market_platform.model.staff.Staff;
import com.doziem.market_platform.payload.dto.StaffDto;
import com.doziem.market_platform.payload.response.StaffResponse;
import org.springframework.stereotype.Component;

@Component
public class StaffMapper {


    public static Staff toEntity(StaffDto dto, User user, Department department, StoreBranch branch) {

        return Staff.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .position(dto.getPosition())
                .hireDate(dto.getHireDate())
                .rank(dto.getRank())
                .roleTitle(dto.getRoleTitle())
                .userAccount(user)
                .department(department)
                .branch(branch)
                .status(dto.getStatus())
                .active(dto.isActive())
                .build();
    }

    public static StaffResponse toResponse(Staff staff) {
        return StaffResponse.builder()
                .staffId(staff.getStaffId())
                .firstName(staff.getFirstName())
                .lastName(staff.getLastName())
                .position(staff.getPosition())
                .rank(staff.getRank())
                .roleTitle(staff.getRoleTitle())
                .hireDate(staff.getHireDate())
                .status(staff.getStatus())
                .active(staff.isActive())
                .departmentId(staff.getDepartment().getDepartmentId())
                .departmentName(staff.getDepartment().getDepartmentName())
                .branchId(staff.getBranch().getBranchId())
                .branchName(staff.getBranch().getBranchName())
                .storeName(staff.getBranch().getStore().getStoreName())
                .userEmail(staff.getUserAccount().getEmail())
                .username(staff.getUserAccount().getUsername())
                .build();
    }
}
