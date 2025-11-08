package com.doziem.market_platform.repository;

import com.doziem.market_platform.model.Store;
import com.doziem.market_platform.model.StoreBranch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StoreBranchRepository extends JpaRepository<StoreBranch, String> {


    Optional<StoreBranch> findByBranchNameIgnoreCase(String branchName);
}
