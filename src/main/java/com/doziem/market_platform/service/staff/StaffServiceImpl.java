package com.doziem.market_platform.service.staff;

import com.doziem.market_platform.enums.EmploymentStatus;
import com.doziem.market_platform.enums.Role;
import com.doziem.market_platform.exception.CustomException;
import com.doziem.market_platform.mapper.DepartmentMapper;
import com.doziem.market_platform.mapper.StaffMapper;
import com.doziem.market_platform.model.StoreBranch;
import com.doziem.market_platform.model.User;
import com.doziem.market_platform.model.staff.Department;
import com.doziem.market_platform.model.staff.Staff;
import com.doziem.market_platform.payload.request.StaffRequest;
import com.doziem.market_platform.payload.response.StaffResponse;
import com.doziem.market_platform.repository.DepartmentRepository;
import com.doziem.market_platform.repository.StaffRepository;
import com.doziem.market_platform.repository.StoreBranchRepository;
import com.doziem.market_platform.repository.UserRepository;
import com.doziem.market_platform.system.Result;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService{

    private static final Logger log = LoggerFactory.getLogger(StaffServiceImpl.class);
    private final StaffRepository staffRepository;
    private final DepartmentRepository departmentRepository;
    private final StoreBranchRepository branchRepository;
    private final UserRepository userRepository;

    @Override
    public Result createStaff(StaffRequest request) {

        Staff staff = StaffMapper.toEntity(request);

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new CustomException("User not found"));

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));
        StoreBranch branch = branchRepository.findById(request.getBranchId())
                .orElseThrow(() -> new RuntimeException("Branch not found"));

        if(!user.getRole().equals(Role.HR_ADMIN)){
            throw new CustomException("Only HR Admin can create Staff");
        }

        staff.setUserAccount(user);

        staff.setDepartment(department);

        if(!branch.isMainBranch()){
            throw new CustomException("Only main store branch can create Staff");
        }
        staff.setBranch(branch);

        Staff saved = staffRepository.save(staff);
        return new Result(true, "Staff Created Successfully" , StaffMapper.toResponse(saved));
    }

    @Override
    public StaffResponse updateStaff(String staffId, StaffRequest request) {

        Staff staff = staffRepository.findById(staffId).orElseThrow(() -> new CustomException("Staff  not found"));

        StaffMapper.updateEntity(staff, request);

        if (request.getUserId() != null) {
            User user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new CustomException("User not found"));
            staff.setUserAccount(user);
        }

        if (request.getDepartmentId() != null) {
            Department department = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> new CustomException("Department not found"));
            staff.setDepartment(department);
        }

        if (request.getBranchId() != null) {
            StoreBranch branch = branchRepository.findById(request.getBranchId())
                    .orElseThrow(() -> new CustomException("Branch not found"));
            staff.setBranch(branch);
        }

        Staff updated = staffRepository.save(staff);
        return StaffMapper.toResponse(updated);
    }

    @Override
    public Result deleteStaff(String staffId) {
        try {
            if (staffRepository.existsById(staffId))
                staffRepository.deleteById(staffId);
            return new Result(true,"Staff Deleted Successfully");

        }catch (CustomException e){
            log.error("Error Deleting Staff {}", e.getMessage());
            return new Result( false,"Staff not found");
        }

    }

    @Override
    public Result getStaffById(String staffId) {
        try {
            Staff staff = staffRepository.findById(staffId)
                    .orElseThrow(() -> new CustomException("Staff not found"));
            return new Result(true, "Staff retrieved successfully" , StaffMapper.toResponse(staff));
        }catch (CustomException  ex){
            log.error("Error Retrieving Staff {} ", ex.getMessage());
            return new Result(false, "Error Retrieving Staff");
        }

    }

    @Override
    public List<StaffResponse> getAllStaff() {
        return StaffMapper.toResponseList(staffRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<StaffResponse> searchByName(String name) {
        List<Staff> results = staffRepository.searchByName(name);
        return StaffMapper.toResponseList(results);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StaffResponse> findByDepartment(Department department) {
        List<Staff> results = staffRepository.findByDepartment(department);
        return StaffMapper.toResponseList(results);
    }

    @Override
    public List<StaffResponse> findByBranch(String branchId) {
        return List.of();
    }

    @Override
    public List<StaffResponse> findByStatus(EmploymentStatus status) {
        return List.of();
    }

    @Override
    public List<StaffResponse> findByActiveStatus(boolean active) {
        return List.of();
    }

    @Override
    public List<StaffResponse> findByRoleTitle(String roleTitle) {
        return List.of();
    }

    @Override
    public List<StaffResponse> findByHireDateRange(ZonedDateTime startDate, ZonedDateTime endDate) {
        return List.of();
    }

    @Override
    @Transactional(readOnly = true)
    public List<StaffResponse> searchStaff(String name, String departmentId, String branchId, EmploymentStatus status, Boolean active, String roleTitle, ZonedDateTime startDate, ZonedDateTime endDate) {
        List<Staff> results = staffRepository.searchStaff((name == null || name.isBlank()) ? null : name, departmentId, branchId, status != null ? status.name() : null, active, (roleTitle == null || roleTitle.isBlank()) ? null : roleTitle, startDate, endDate);

        return StaffMapper.toResponseList(results);
    }
}
