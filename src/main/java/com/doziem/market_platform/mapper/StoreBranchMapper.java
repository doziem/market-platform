package com.doziem.market_platform.mapper;

import com.doziem.market_platform.model.Store;
import com.doziem.market_platform.model.StoreBranch;
import com.doziem.market_platform.model.WorkHour;
import com.doziem.market_platform.payload.dto.WorkHourDto;
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
                .weekday(toWorkHourEntity(dto.weekday()))
                .saturday(toWorkHourEntity(dto.saturday()))
                .sunday(toWorkHourEntity(dto.sunday()))
                .build();
    }

    public static   StoreBranchResponse toResponse(StoreBranch branch) {
        if (branch == null) return null;
        String storeId = branch.getStore() != null ? branch.getStore().getStoreId() : null;
        String storeName = branch.getStore() != null ? branch.getStore().getStoreName() : null;
        return  StoreBranchResponse.builder()
                .branchId(branch.getBranchId())
                .branchName(branch.getBranchName())
                .address(branch.getAddress())
                .city(branch.getCity())
                .state(branch.getState())
                .country(branch.getCountry())
                .mainBranch(branch.isMainBranch())
                .storeId(storeId)
                .storeName(storeName)
                .weekday(toWorkHourDto(branch.getWeekday()))
                .saturday(toWorkHourDto(branch.getSaturday()))
                .sunday(toWorkHourDto(branch.getSunday()))
                .build();
    }

    // Convert WorkHour to WorkHourDto
    private static WorkHourDto toWorkHourDto(WorkHour workHour) {
        if (workHour == null || workHour.getOpenTime() == null || workHour.getCloseTime() == null) {
            return null;
        }

        return WorkHourDto.builder()
                .openTime(workHour.getOpenTime())
                .closeTime(workHour.getCloseTime())
                .build();
    }

    // Convert WorkHourDto to WorkHour
    private static WorkHour toWorkHourEntity(WorkHourDto dto) {
        if (dto == null || dto.getOpenTime() == null || dto.getCloseTime() == null) {
            return null;
        }
        return WorkHour.builder()
                .openTime(dto.getOpenTime())
                .closeTime(dto.getCloseTime())
                .build();
    }
}
