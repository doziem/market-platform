package com.doziem.market_platform.payload.response;

import com.doziem.market_platform.payload.dto.WorkHourDto;
import lombok.Builder;

@Builder
public record StoreBranchResponse(
        String branchId,
        String branchName,
        String address,
        String city,
        String state,
        String country,
        String phoneNumber,
        boolean mainBranch,
        String  storeId,
        String storeName,
        WorkHourDto weekday,
        WorkHourDto saturday,
        WorkHourDto sunday
) {
}
