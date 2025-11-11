package com.doziem.market_platform.service.staff;

import com.doziem.market_platform.enums.EmploymentStatus;
import com.doziem.market_platform.model.staff.Department;
import com.doziem.market_platform.payload.request.StaffRequest;
import com.doziem.market_platform.payload.response.StaffResponse;
import com.doziem.market_platform.system.Result;

import java.time.ZonedDateTime;
import java.util.List;

public interface StaffService {
    Result createStaff(StaffRequest request);

    StaffResponse updateStaff(String staffId, StaffRequest request);

    Result deleteStaff(String staffId);

    Result getStaffById(String staffId);

    List<StaffResponse> getAllStaff();


    /**
     * Find staff by first or last name (partial match, case-insensitive)
     */
    List<StaffResponse> searchByName(String name);

    /**
     * Find staff by department ID
     */
    List<StaffResponse> findByDepartment(Department department);

    /**
     * Find staff by branch ID
     */
    List<StaffResponse> findByBranch(String branchId);

    /**
     * Find staff by employment status (e.g., FULL_TIME, CONTRACT)
     */
    List<StaffResponse> findByStatus(EmploymentStatus status);

    /**
     * Find staff by active/inactive flag
     */
    List<StaffResponse> findByActiveStatus(boolean active);

    /**
     * Find staff by role title (e.g., "Manager", "Cashier")
     */
    List<StaffResponse> findByRoleTitle(String roleTitle);

    /**
     * Find staff hired between two dates
     */
    List<StaffResponse> findByHireDateRange(ZonedDateTime startDate, ZonedDateTime endDate);

    /**
     * General multi-filter search (combines parameters)
     * Any parameter can be null or optional.
     */
    List<StaffResponse> searchStaff(
            String name,
            String departmentId,
            String branchId,
            EmploymentStatus status,
            Boolean active,
            String roleTitle,
            ZonedDateTime startDate,
            ZonedDateTime endDate
    );
}
