package com.doziem.market_platform.repository;

import com.doziem.market_platform.model.staff.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StaffRepository extends JpaRepository<Staff, String> {
    List<Staff> findByDepartment_DepartmentId(String departmentId);
    List<Staff> findByStoreBranch_BranchId(String branchId);
    List<Staff> findByActiveTrue();
}
