package com.doziem.market_platform.mapper;

import com.doziem.market_platform.enums.StoreType;
import com.doziem.market_platform.model.Store;
import com.doziem.market_platform.model.WorkHour;
import com.doziem.market_platform.payload.dto.WorkHourDto;
import com.doziem.market_platform.payload.request.StoreBranchRequest;
import com.doziem.market_platform.payload.response.StoreBranchResponse;
import org.springframework.stereotype.Component;

@Component
public class StoreBranchMapper {

    public Store toEntity(StoreBranchRequest dto, Store parentStore) {
        return Store.builder()
                .storeName(dto.branchName())
                .address(dto.address())
                .city(dto.city())
                .state(dto.state())
                .country(dto.country())
                .phoneNumber(dto.phoneNumber())
                .isHeadQuarter(false)
                .mainBranch(dto.mainBranch())
                .storeType(StoreType.BRANCH)
                .parentStore(parentStore)
                .weekday(toWorkHourEntity(dto.weekday()))
                .saturday(toWorkHourEntity(dto.saturday()))
                .sunday(toWorkHourEntity(dto.sunday()))
                .build();
    }

    public static StoreBranchResponse toResponse(Store branch) {
        if (branch == null) {
            return null;
        }

        String storeId = branch.getParentStore() != null ? branch.getParentStore().getStoreId() : null;
        String storeName = branch.getParentStore() != null ? branch.getParentStore().getStoreName() : null;

        return StoreBranchResponse.builder()
                .branchId(branch.getStoreId())
                .branchName(branch.getStoreName())
                .address(branch.getAddress())
                .city(branch.getCity())
                .state(branch.getState())
                .country(branch.getCountry())
                .phoneNumber(branch.getPhoneNumber())
                .mainBranch(branch.isMainBranch())
                .storeId(storeId)
                .storeName(storeName)
                .weekday(toWorkHourDto(branch.getWeekday()))
                .saturday(toWorkHourDto(branch.getSaturday()))
                .sunday(toWorkHourDto(branch.getSunday()))
                .build();
    }

    private static WorkHourDto toWorkHourDto(WorkHour workHour) {
        if (workHour == null || workHour.getOpenTime() == null || workHour.getCloseTime() == null) {
            return null;
        }

        return WorkHourDto.builder()
                .openTime(workHour.getOpenTime())
                .closeTime(workHour.getCloseTime())
                .build();
    }

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
