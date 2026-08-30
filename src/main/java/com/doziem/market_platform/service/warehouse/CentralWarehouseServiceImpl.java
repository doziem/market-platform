package com.doziem.market_platform.service.warehouse;

import com.doziem.market_platform.exception.ResourceNotFoundException;
import com.doziem.market_platform.mapper.CentralWarehouseMapper;
import com.doziem.market_platform.model.CentralWarehouse;
import com.doziem.market_platform.model.Store;
import com.doziem.market_platform.payload.dto.CentralWarehouseDto;
import com.doziem.market_platform.payload.response.CentralWarehouseResponse;
import com.doziem.market_platform.repository.CentralWarehouseRepository;
import com.doziem.market_platform.repository.StoreRepository;
import com.doziem.market_platform.system.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CentralWarehouseServiceImpl  implements CentralWarehouseService {
    private final CentralWarehouseRepository centralWarehouseRepository;
    private final StoreRepository storeRepository;

    @Override
    public CentralWarehouseResponse createWarehouse(CentralWarehouseDto request) {

        Store store = storeRepository.findById(request.getStoreId())
                .orElseThrow(() -> new RuntimeException("Store not found"));

        CentralWarehouse warehouse = CentralWarehouseMapper.toEntity(request, store);
        CentralWarehouse savedWarehouse = centralWarehouseRepository.save(warehouse);

        return CentralWarehouseMapper.toResponse(savedWarehouse);
    }

    @Override
    public CentralWarehouseResponse getWarehouseById(String id) {

        return centralWarehouseRepository.findById(id)
                .map(CentralWarehouseMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Central Warehouse not found"));
    }

    @Override
    public List<CentralWarehouseResponse> getAllWarehouses() {
        return CentralWarehouseMapper.toResponseList(centralWarehouseRepository.findAll());
    }

    @Override
    public CentralWarehouseResponse updateWarehouse(String id, CentralWarehouseDto request) {
        CentralWarehouse warehouse = centralWarehouseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Central warehouse not found"));

        CentralWarehouse updatedWarehouse = CentralWarehouseMapper.updateEntity(warehouse, request);
        CentralWarehouse validatedWarehouse =    centralWarehouseRepository.save(updatedWarehouse);

        return CentralWarehouseMapper.toResponse(validatedWarehouse);
    }

    @Override
    public void deleteWarehouse(String id) {
        if(centralWarehouseRepository.existsById(id)){
            throw new ResourceNotFoundException("Central warehouse not found");
        }
        centralWarehouseRepository.deleteById(id);
    }

    @Override
    public Result getCentralWarehouseByStoreId(String storeId) {
        CentralWarehouse warehouse = centralWarehouseRepository.findByStore_StoreId(storeId);
        if (warehouse != null) {
            CentralWarehouseDto dto = CentralWarehouseMapper.toDto(warehouse);
            return new Result(true, "Central warehouse found", dto);
        }
        return new Result(false, "Central warehouse not found for the given store ID");
    }
}
