package com.doziem.market_platform.service.logistic;

import com.doziem.market_platform.payload.request.LogisticsCompanyRequest;
import com.doziem.market_platform.payload.response.LogisticsCompanyResponse;

import java.util.List;

public interface LogisticsCompanyService {

    LogisticsCompanyResponse create(LogisticsCompanyRequest request);

    LogisticsCompanyResponse update(String logisticsId, LogisticsCompanyRequest request);

    LogisticsCompanyResponse getById(String logisticsId);

    List<LogisticsCompanyResponse> getAll();

    void delete(String logisticsId);
}
