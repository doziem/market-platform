package com.doziem.market_platform.mapper;

import com.doziem.market_platform.model.StoreBranch;
import com.doziem.market_platform.model.staff.Department;
import com.doziem.market_platform.model.staff.HumanResource;
import com.doziem.market_platform.model.staff.Staff;
import com.doziem.market_platform.payload.dto.DepartmentDto;
import com.doziem.market_platform.payload.response.StaffResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DepartmentMapper {

    public static Department toEntity(DepartmentDto dto, HumanResource hr, StoreBranch branch, List<Staff> staffList){
        return Department.builder()
                .departmentName(dto.getDepartmentName())
                .humanResource(hr)
                .branch(branch)
                .staffList(staffList)
                .description(dto.getDescription())
                .build();
    }

    public static DepartmentDto toDto(Department dept){

        List<StaffResponse> staffIds = dept.getStaffList()
                        .stream()
                        .map(StaffMapper::toResponse)
                        .toList() ;

        return DepartmentDto.builder()
                .departmentId(dept.getDepartmentId())
                .departmentName(dept.getDepartmentName())
                .humanResourceId(dept.getHumanResource().getHumanResourceId())
                .branchId(dept.getBranch().getBranchId())
                .staff(staffIds)
                .description(dept.getDescription())
                .build();
    }
}
