package com.doziem.market_platform.service.warehouse;

import com.doziem.market_platform.enums.RequestStatus;
import com.doziem.market_platform.exception.ResourceNotFoundException;
import com.doziem.market_platform.mapper.StateReplenishmentMapper;
import com.doziem.market_platform.model.Product;
import com.doziem.market_platform.model.StateReplenishmentRequest;
import com.doziem.market_platform.model.StateWarehouse;
import com.doziem.market_platform.payload.dto.StateReplenishmentRequestDto;
import com.doziem.market_platform.payload.response.StateReplenishmentRequestResponse;
import com.doziem.market_platform.repository.ProductRepository;
import com.doziem.market_platform.repository.StateReplenishmentRequestRepository;
import com.doziem.market_platform.repository.StateWarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class StateReplenishmentRequestServiceImpl implements StateReplenishmentRequestService {
    private final StateReplenishmentRequestRepository requestRepository;
    private final StateWarehouseRepository warehouseRepository;
    private final ProductRepository productRepository;

    @Override
    public StateReplenishmentRequestResponse createRequest(StateReplenishmentRequestDto dto) {

        StateWarehouse warehouse = warehouseRepository.findById(dto.getStateWarehouseId())
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found"));

        // Convert main request
        StateReplenishmentRequest request = StateReplenishmentMapper.toEntity(dto);
        request.setStateWarehouse(warehouse);
        request.setStatus(RequestStatus.PENDING);

        // Convert each item inside request
        request.getItems().forEach(item -> {
            Product product = productRepository.findById(item.getProduct().getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

            item.setProduct(product);
            item.setStateRequest(request);
        });

        StateReplenishmentRequest saved = requestRepository.save(request);
        return StateReplenishmentMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public StateReplenishmentRequestResponse getRequestById(String requestId) {
        StateReplenishmentRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found"));
        return StateReplenishmentMapper.toResponse(request);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StateReplenishmentRequestResponse> getAllRequests() {
        return requestRepository.findAll().stream()
                .map(StateReplenishmentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public StateReplenishmentRequestResponse approveRequest(String requestId, String approvedBy) {
        StateReplenishmentRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found"));

        request.setStatus(RequestStatus.APPROVED);
        request.setApprovedBy(approvedBy);
        request.setApprovedDate(ZonedDateTime.now());

        return StateReplenishmentMapper.toResponse(request);
    }

    @Override
    public StateReplenishmentRequestResponse rejectRequest(String requestId, String approvedBy) {
        StateReplenishmentRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found"));

        request.setStatus(RequestStatus.REJECTED);
        request.setApprovedBy(approvedBy.toLowerCase().trim());
        request.setApprovedDate(ZonedDateTime.now());

        return StateReplenishmentMapper.toResponse(request);
    }
}
