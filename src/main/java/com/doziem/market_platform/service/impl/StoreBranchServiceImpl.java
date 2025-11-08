package com.doziem.market_platform.service.impl;

import com.doziem.market_platform.exception.CustomException;
import com.doziem.market_platform.mapper.StoreBranchMapper;
import com.doziem.market_platform.model.Store;
import com.doziem.market_platform.model.StoreBranch;
import com.doziem.market_platform.payload.request.StoreBranchRequest;
import com.doziem.market_platform.payload.response.StoreBranchResponse;
import com.doziem.market_platform.repository.StoreBranchRepository;
import com.doziem.market_platform.repository.StoreRepository;
import com.doziem.market_platform.service.StoreBranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoreBranchServiceImpl  implements StoreBranchService {

    private final StoreBranchRepository storeBranchRepository;
    private final StoreRepository storeRepository;
    private final StoreBranchMapper storeBranchMapper;

    @Transactional
    public StoreBranchResponse createBranch(String  storeId, StoreBranchRequest request) {
        // Validate store existence and state
        Store store = validateStore(storeId);

        // Prevent duplicate branch names under same store
        Optional<StoreBranch> existingBranch = storeBranchRepository.findByBranchNameIgnoreCase(request.branchName());

        if (existingBranch.isPresent() && store.getStoreId().equals(storeId)) {
            throw new CustomException("A branch with this name already exists for the store.");
        }

        // Convert to entity and save
        StoreBranch branch = storeBranchMapper.toEntity(request, store);
        StoreBranch saved = storeBranchRepository.save(branch);

        return StoreBranchMapper.toResponse(saved);
    }

    public List<StoreBranchResponse> getBranches(String storeId) {
        return storeBranchRepository.findAll().stream()
                .filter(branch -> branch.getStore() != null && storeId.equals(branch.getStore().getStoreId()))
                .map(StoreBranchMapper::toResponse)
                .toList();
    }

    private Store validateStore(String  storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomException("Store not found"));
    }
}


