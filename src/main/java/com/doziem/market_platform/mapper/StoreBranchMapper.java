package com.doziem.market_platform.mapper;

import com.doziem.market_platform.model.Store;
import com.doziem.market_platform.model.StoreBranch;
import com.doziem.market_platform.payload.request.StoreBranchRequest;
import com.doziem.market_platform.payload.response.StoreBranchResponse;
import org.springframework.stereotype.Component;

@Component
public class StoreBranchMapper {

    public StoreBranch toEntity(StoreBranchRequest dto, Store store) {
        return StoreBranch.builder()
                .branchName(dto.branchName())
                .address(dto.address())
                .city(dto.city())
                .state(dto.state())
                .country(dto.country())
                .phoneNumber(dto.phoneNumber())
                .mainBranch(dto.mainBranch())
                .store(store)
                .build();
    }

    public static   StoreBranchResponse toResponse(StoreBranch branch) {
        if (branch == null) return null;
        String storeId = branch.getStore() != null ? branch.getStore().getStoreId() : null;
        String storeName = branch.getStore() != null ? branch.getStore().getStoreName() : null;
        return new StoreBranchResponse(
                branch.getBranchId(),
                branch.getBranchName(),
                branch.getAddress(),
                branch.getCity(),
                branch.getState(),
                branch.getCountry(),
                branch.getPhoneNumber(),
                branch.isMainBranch(),
                storeId,
                storeName
        );
    }
}
