package com.doziem.market_platform.mapper;

import com.doziem.market_platform.model.LogisticsCompany;
import com.doziem.market_platform.payload.request.LogisticsCompanyRequest;
import com.doziem.market_platform.payload.response.LogisticsCompanyResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LogisticsCompanyMapper {

    public static LogisticsCompany toEntity(LogisticsCompanyRequest dto){
        return LogisticsCompany.builder()
                .name(dto.getName())
                .contactPerson(dto.getContactPerson())
                .phoneNumber(dto.getPhoneNumber())
                .email(dto.getEmail())
                .address(dto.getAddress())
                .regionCovered(dto.getRegionCovered())
                .logisticLogoUrl(dto.getLogisticLogoUrl())
                .publicId(dto.getPublicId())
                .build();
    }

    public static LogisticsCompanyResponse toResponse(LogisticsCompany logisticsCompany){
        return LogisticsCompanyResponse.builder()
                .logisticsId(logisticsCompany.getLogisticsId())
                .name(logisticsCompany.getName())
                .contactPerson(logisticsCompany.getContactPerson())
                .phoneNumber(logisticsCompany.getPhoneNumber())
                .email(logisticsCompany.getEmail())
                .address(logisticsCompany.getAddress())
                .regionCovered(logisticsCompany.getRegionCovered())
                .logisticLogoUrl(logisticsCompany.getLogisticLogoUrl())
                .publicId(logisticsCompany.getPublicId())
                .build();
    }

    public static LogisticsCompany updateEntity(LogisticsCompany entity, LogisticsCompanyRequest req) {
        entity.setName(req.getName() != null ? req.getName() : entity.getName());
        entity.setContactPerson(req.getContactPerson() != null ? req.getContactPerson() : entity.getContactPerson());
        entity.setPhoneNumber(req.getPhoneNumber() != null ? req.getPhoneNumber() : entity.getPhoneNumber());
        entity.setEmail(req.getEmail() != null ? req.getEmail() : entity.getEmail());
        entity.setAddress(req.getAddress() != null ? req.getAddress() : entity.getAddress());
        entity.setRegionCovered(req.getRegionCovered()  != null ? req.getRegionCovered() : entity.getRegionCovered());
        return entity;
    }
    public static List<LogisticsCompanyResponse> toResponseList(List<LogisticsCompany> companies) {
        return companies.stream()
                .map(LogisticsCompanyMapper::toResponse)
                .collect(Collectors.toList());
    }
}
