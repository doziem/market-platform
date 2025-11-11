package com.doziem.market_platform.repository;

import com.doziem.market_platform.model.staff.Department;
import com.doziem.market_platform.model.staff.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.List;

public interface StaffRepository extends JpaRepository<Staff, String>  {
    List<Staff> findByDepartment_DepartmentId(String departmentId);

    @Query("""
    SELECT s FROM Staff s
    WHERE LOWER(s.firstName) LIKE LOWER(CONCAT('%', :name, '%'))
       OR LOWER(s.lastName) LIKE LOWER(CONCAT('%', :name, '%'))
""")
    List<Staff> searchByName(@Param("name") String name);


    List<Staff> findByDepartment(Department department);
    List<Staff> findByActiveTrue();

    @Query(value = """
        SELECT * FROM staff s
        WHERE (:name IS NULL OR LOWER(s.first_name) LIKE LOWER(CONCAT('%', :name, '%')))
          AND (:departmentId IS NULL OR s.department_id = :departmentId)
          AND (:branchId IS NULL OR s.branch_id = :branchId)
          AND (:status IS NULL OR s.status = :status)
          AND (:active IS NULL OR s.active = :active)
          AND (:roleTitle IS NULL OR LOWER(s.role_title) LIKE LOWER(CONCAT('%', :roleTitle, '%')))
          AND ( (:startDate IS NULL OR :endDate IS NULL) OR (s.hire_date BETWEEN :startDate AND :endDate))
    """, nativeQuery = true)
    List<Staff> searchStaff(
            @Param("name") String name,
            @Param("departmentId") String departmentId,
            @Param("branchId") String branchId,
            @Param("status") String status,  // Enum is stored as String in DB
            @Param("active") Boolean active,
            @Param("roleTitle") String roleTitle,
            @Param("startDate") ZonedDateTime startDate,
            @Param("endDate") ZonedDateTime endDate
    );
}
