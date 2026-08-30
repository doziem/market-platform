package com.doziem.market_platform.service.impl;

import com.doziem.market_platform.enums.StoreType;
import com.doziem.market_platform.exception.CustomException;
import com.doziem.market_platform.mapper.StoreBranchMapper;
import com.doziem.market_platform.model.Store;
import com.doziem.market_platform.payload.request.StoreBranchRequest;
import com.doziem.market_platform.payload.response.StoreBranchResponse;
import com.doziem.market_platform.repository.StoreRepository;
import com.doziem.market_platform.service.StoreBranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreBranchServiceImpl implements StoreBranchService {

    private final StoreRepository storeRepository;
    private final StoreBranchMapper storeBranchMapper;

    @Transactional
    public StoreBranchResponse createBranch(String storeId, StoreBranchRequest request) {
        Store parentStore = validateStore(storeId);

        if (storeRepository.existsByStoreNameIgnoreCaseAndParentStore_StoreId(request.branchName(), storeId)) {
            throw new CustomException("A branch with this name already exists for the store.");
        }

        Store branch = storeBranchMapper.toEntity(request, parentStore);
        branch.setStoreType(StoreType.BRANCH);
        branch.setParentStore(parentStore);

        Store saved = storeRepository.save(branch);
        parentStore.addBranch(saved);
        storeRepository.save(parentStore);

        return StoreBranchMapper.toResponse(saved);
    }

    public List<StoreBranchResponse> getBranches(String storeId) {
        return storeRepository.findAll().stream()
                .filter(branch -> branch.getParentStore() != null && storeId.equals(branch.getParentStore().getStoreId()))
                .map(StoreBranchMapper::toResponse)
                .toList();
    }

    private Store validateStore(String storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomException("Store not found"));
    }
}
