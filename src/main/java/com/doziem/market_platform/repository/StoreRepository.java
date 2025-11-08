package com.doziem.market_platform.repository;

import com.doziem.market_platform.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, String> {
    boolean existsByStoreName(String storeName);
//    Store findByStoreAdminId(String adminId);
}
