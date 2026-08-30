package com.doziem.market_platform.mapper;

import com.doziem.market_platform.enums.Role;
import com.doziem.market_platform.enums.StoreType;
import com.doziem.market_platform.model.Store;
import com.doziem.market_platform.model.User;
import com.doziem.market_platform.model.WorkHour;
import com.doziem.market_platform.payload.dto.UserDto;
import com.doziem.market_platform.payload.dto.WorkHourDto;
import com.doziem.market_platform.payload.request.StoreRequest;
import com.doziem.market_platform.payload.response.StoreBranchResponse;
import com.doziem.market_platform.payload.response.StoreResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StoreMapper {

    public static StoreRequest toDto(Store store) {
        if (store == null) {
            return null;
        }

        return StoreRequest.builder()
                .storeName(store.getStoreName())
                .storeType(store.getStoreType())
                .status(store.getStatus())
                .address(store.getAddress())
                .city(store.getCity())
                .lga(store.getLga())
                .state(store.getState())
                .country(store.getCountry())
                .phoneNumber(store.getPhoneNumber())
                .zipCode(store.getZipCode())
                .countryCode(store.getCountryCode())
                .iso(store.getIso())
                .isHeadQuarter(store.isHeadQuarter())
                .parentStoreId(store.getParentStore() != null ? store.getParentStore().getStoreId() : null)
                .weekday(toWorkHourDto(store.getWeekday()))
                .saturday(toWorkHourDto(store.getSaturday()))
                .sunday(toWorkHourDto(store.getSunday()))
                .build();
    }

    public static Store toEntity(StoreRequest dto, User user) {
        if (dto == null) {
            return null;
        }

        Store store = Store.builder()
                .storeName(dto.getStoreName())
                .storeType(dto.getStoreType() != null ? dto.getStoreType() : StoreType.HEADQUARTERS)
                .user(user)
                .status(dto.getStatus())
                .address(dto.getAddress())
                .city(dto.getCity())
                .lga(dto.getLga())
                .state(dto.getState())
                .country(dto.getCountry())
                .phoneNumber(dto.getPhoneNumber())
                .zipCode(dto.getZipCode())
                .countryCode(dto.getCountryCode())
                .createdAt(ZonedDateTime.now())
                .updatedAt(ZonedDateTime.now())
                .iso(dto.getIso())
                .isHeadQuarter(dto.isHeadQuarter())
                .weekday(toWorkHourEntity(dto.getWeekday()))
                .saturday(toWorkHourEntity(dto.getSaturday()))
                .sunday(toWorkHourEntity(dto.getSunday()))
                .build();

        if (dto.getParentStoreId() != null) {
            store.setParentStore(Store.builder().storeId(dto.getParentStoreId()).build());
        }

        return store;
    }

    public static StoreResponse storeResponse(Store store) {
        if (store == null) {
            return null;
        }

        User user = store.getUser();
        List<StoreBranchResponse> storeBranch = store.getStoreBranches().stream()
                .map(StoreBranchMapper::toResponse)
                .toList();

        UserDto userDto = UserDto.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .displayName(user.getDisplayName())
                .username(user.getUsername())
                .role(Role.valueOf(String.valueOf(user.getRole())))
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .lastLogin(user.getLastLogin())
                .build();

        return StoreResponse.builder()
                .storeId(store.getStoreId())
                .storeName(store.getStoreName())
                .storeType(store.getStoreType())
                .status(store.getStatus())
                .address(store.getAddress())
                .city(store.getCity())
                .lga(store.getLga())
                .state(store.getState())
                .country(store.getCountry())
                .user(userDto)
                .storeLogo(store.getStoreLogo())
                .hasLogo(store.getStoreLogo() != null)
                .phoneNumber(store.getPhoneNumber())
                .zipCode(store.getZipCode())
                .countryCode(store.getCountryCode())
                .iso(store.getIso())
                .isHeadQuarter(store.isHeadQuarter())
                .mainBranch(store.isMainBranch())
                .parentStoreId(store.getParentStore() != null ? store.getParentStore().getStoreId() : null)
                .parentStoreName(store.getParentStore() != null ? store.getParentStore().getStoreName() : null)
                .storeBranches(storeBranch)
                .createdAt(ZonedDateTime.now())
                .updatedAt(ZonedDateTime.now())
                .weekday(toWorkHourDto(store.getWeekday()))
                .saturday(toWorkHourDto(store.getSaturday()))
                .sunday(toWorkHourDto(store.getSunday()))
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
