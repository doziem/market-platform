package com.doziem.market_platform.service;

import com.doziem.market_platform.payload.request.StoreBranchRequest;
import com.doziem.market_platform.payload.response.StoreBranchResponse;

import java.util.List;

public interface StoreBranchService {

    StoreBranchResponse createBranch(String  storeId, StoreBranchRequest request);

    List<StoreBranchResponse> getBranches(String storeId);


}
