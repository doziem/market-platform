package com.doziem.market_platform.mapper;

import com.doziem.market_platform.enums.Role;
import com.doziem.market_platform.model.*;
import com.doziem.market_platform.payload.dto.UserDto;
import com.doziem.market_platform.payload.dto.WorkHourDto;
import com.doziem.market_platform.payload.request.StoreRequest;
import com.doziem.market_platform.payload.response.StoreBranchResponse;
import com.doziem.market_platform.payload.response.StoreResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StoreMapper {
    private  final StoreBranchMapper storeBranchMapper;

    public static StoreRequest toDto(Store store){

     if(store ==null){
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
             .weekday(toWorkHourDto(store.getWeekday()))
             .saturday(toWorkHourDto(store.getSaturday()))
             .sunday(toWorkHourDto(store.getSunday()))
             .build();
    }

    public static Store toEntity(StoreRequest dto, User user) {
        if (dto == null) {
            return null;
        }

        return Store.builder()
                .storeName(dto.getStoreName())
                .storeType(dto.getStoreType())
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
                .weekday(toWorkHourEntity(dto.getWeekday()))
                .saturday(toWorkHourEntity(dto.getSaturday()))
                .sunday(toWorkHourEntity(dto.getSunday()))
                .build();
    }

    public static StoreResponse storeResponse(Store store){

        if (store == null) return null;

        User user = store.getUser();

        List<StoreBranchResponse> storeBranch = store.getStoreBranches().stream()
                .map(StoreBranchMapper::toResponse)
                .toList();

        List<Product> products = new ArrayList<>(store.getProducts());


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
                .storeBranches(storeBranch)
                .products(products)
                .createdAt(ZonedDateTime.now())
                .updatedAt(ZonedDateTime.now())
                .weekday(toWorkHourDto(store.getWeekday()))
                .saturday(toWorkHourDto(store.getSaturday()))
                .sunday(toWorkHourDto(store.getSunday()))
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

//
//    public static List<StoreBranchRequest> toStudentDTOList(List<StoreBranch> storeBranch) {
//        if (storeBranch == null) return null;
//        return storeBranch.stream()
//                .map(StoreMapper::toDto)
//                .collect(Collectors.toList());
//    }
}
