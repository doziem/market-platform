package com.doziem.market_platform.service.logistic;

import com.doziem.market_platform.exception.ResourceNotFoundException;
import com.doziem.market_platform.mapper.LogisticsCompanyMapper;
import com.doziem.market_platform.model.LogisticsCompany;
import com.doziem.market_platform.payload.request.LogisticsCompanyRequest;
import com.doziem.market_platform.payload.response.LogisticsCompanyResponse;
import com.doziem.market_platform.repository.LogisticsCompanyRepository;
import com.doziem.market_platform.service.cloudinary.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LogisticsCompanyServiceImpl implements LogisticsCompanyService {

    private final LogisticsCompanyRepository logisticsCompanyRepository;
    private final CloudinaryService cloudinaryService;

    @Override
    public LogisticsCompanyResponse create(LogisticsCompanyRequest request) {

        Map<String, String> upload = cloudinaryService.upload(request.getLogisticLogoImage(), "logistics_companies");
        request.setLogisticLogoUrl(upload.get("url"));
        request.setPublicId(upload.get("public_id"));

        LogisticsCompany logisticsCompany = LogisticsCompanyMapper.toEntity(request);

        return LogisticsCompanyMapper.toResponse(logisticsCompanyRepository.save(logisticsCompany));
    }

    @Override
    public LogisticsCompanyResponse update(String logisticsId, LogisticsCompanyRequest request) {
        LogisticsCompany logisticsCompany = logisticsCompanyRepository.findById(logisticsId).orElseThrow(
                () -> new ResourceNotFoundException("Logistics Company not found")
        );
        if(request.getLogisticLogoImage() !=null){
            // Delete old image from Cloudinary
            if(logisticsCompany.getPublicId() != null && !logisticsCompany.getPublicId().isEmpty()){
                cloudinaryService.delete(logisticsCompany.getPublicId());
            }
            // Upload new image to Cloudinary
            Map<String, String> upload = cloudinaryService.upload(request.getLogisticLogoImage(), "logistics_companies");
            request.setLogisticLogoUrl(upload.get("url"));
            request.setPublicId(upload.get("public_id"));
        } else {
            request.setLogisticLogoUrl(logisticsCompany.getLogisticLogoUrl());
            request.setPublicId(logisticsCompany.getPublicId());
        }
        LogisticsCompany updatedLogisticsCompany = LogisticsCompanyMapper.updateEntity(logisticsCompany, request);

        return LogisticsCompanyMapper.toResponse(logisticsCompanyRepository.save(updatedLogisticsCompany));
    }

    @Override
    public LogisticsCompanyResponse getById(String logisticsId) {
        return LogisticsCompanyMapper.toResponse(
                logisticsCompanyRepository.findById(logisticsId).orElseThrow(
                        () -> new ResourceNotFoundException("Logistics Company not found")
                ));
    }

    @Override
    public List<LogisticsCompanyResponse> getAll() {
        return LogisticsCompanyMapper.toResponseList(logisticsCompanyRepository.findAll());
    }

    @Override
    public void delete(String logisticsId) {
        LogisticsCompany logisticsCompany = logisticsCompanyRepository.findById(logisticsId).orElseThrow(
                () -> new ResourceNotFoundException("Logistics Company not found")
        );

      if(logisticsCompany.getPublicId() != null && !logisticsCompany.getPublicId().isEmpty()){
          cloudinaryService.delete(logisticsCompany.getPublicId());
      }
        logisticsCompanyRepository.delete(logisticsCompany);
    }

}
