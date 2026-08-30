package com.doziem.market_platform.repository;

import com.doziem.market_platform.model.staff.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, String> {
}
