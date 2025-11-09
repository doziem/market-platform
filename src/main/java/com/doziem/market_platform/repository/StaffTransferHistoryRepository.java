package com.doziem.market_platform.repository;

import com.doziem.market_platform.model.staff.StaffTransferHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffTransferHistoryRepository extends JpaRepository<StaffTransferHistory, String> {
}
