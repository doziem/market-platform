package com.doziem.market_platform.service.impl;

import com.doziem.market_platform.exception.CustomException;
import com.doziem.market_platform.model.CentralWarehouse;
import com.doziem.market_platform.model.Store;
import com.doziem.market_platform.payload.dto.CentralWarehouseDto;
import com.doziem.market_platform.payload.response.StoreResponse;
import com.doziem.market_platform.repository.CentralWarehouseRepository;
import com.doziem.market_platform.repository.StoreRepository;
import com.doziem.market_platform.service.StoreService;
import com.doziem.market_platform.system.Result;

import  com.doziem.market_platform.service.CentralWarehouseService;
import com.doziem.market_platform.mapper.CentralWarehouseMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CentralWarehouseServiceImpl implements CentralWarehouseService {

    private static final Logger log = LoggerFactory.getLogger(CentralWarehouseServiceImpl.class);
    private final CentralWarehouseMapper centralWarehouseMapper;
    private final CentralWarehouseRepository centralWarehouseRepository;
    private final StoreServiceImpl storeService;
    private final StoreRepository storeRepository;

    @Override
    public Result createCentralWarehouse(String storeId, CentralWarehouseDto dto) {
        StoreResponse store = storeService.getStoreByStoreId(storeId);

        if(!store.isHeadQuarter()) {
            log.info("Store {} is not Head Quarter, cannot create central warehouse", store.getStoreName());
            throw  new CustomException( "Only Head Quarter store can create central warehouse");
        }

        CentralWarehouse centralWarehouse = centralWarehouseMapper.toEntity(dto, Store.builder()
                .storeId(storeId).build());
        CentralWarehouse savedWarehouse = centralWarehouseRepository.save(centralWarehouse);

        CentralWarehouseDto warehouseDto = CentralWarehouseMapper.toDto(savedWarehouse);
        return new Result(true, "Central Warehouse created successfully", warehouseDto);

    }

    @Override
    public Result getCentralWarehouseByStoreId(String storeId) {

        return storeRepository.findById(storeId).map(store -> {
            CentralWarehouse warehouse = centralWarehouseRepository.findByStore_StoreId(storeId);
            if (warehouse == null) {
                log.info("No central warehouse found for store ID: {}", storeId);
                return new Result(false, "No central warehouse found for this store", null);
            }
            CentralWarehouseDto warehouseDto = CentralWarehouseMapper.toDto(warehouse);
            return new Result(true, "Central Warehouse retrieved successfully", warehouseDto);
        }).orElseThrow(() -> new CustomException("Store not found with ID: " + storeId));
    }

    @Override
    public Result updateCentralWarehouse(String centralWarehouseId, CentralWarehouseDto dto) {

        try {

            CentralWarehouse existingWarehouse = centralWarehouseRepository.findById(centralWarehouseId)
                    .orElseThrow(() -> new CustomException("Central Warehouse not found"));

            existingWarehouse.setName(dto.getName() != null ? dto.getName() : existingWarehouse.getName());
            existingWarehouse.setAddress(dto.getAddress() != null ? dto.getAddress() : existingWarehouse.getAddress());
            existingWarehouse.setCity(dto.getCity() != null ? dto.getCity() : existingWarehouse.getCity());
            existingWarehouse.setState(dto.getState() != null ? dto.getState() : existingWarehouse.getState());
            existingWarehouse.setCountry(dto.getCountry() != null ? dto.getCountry() : existingWarehouse.getCountry());
            existingWarehouse.setStore(existingWarehouse.getStore());
            CentralWarehouse updatedWarehouse = centralWarehouseRepository.save(existingWarehouse);
            CentralWarehouseDto warehouseDto = CentralWarehouseMapper.toDto(updatedWarehouse);
            return new Result(true, "Central Warehouse updated successfully", warehouseDto);
        } catch (NumberFormatException e) {
            throw new CustomException("Invalid Central Warehouse ID format");
        }
    }

    @Override
    public Result getCentralWarehouseById(String centralWarehouseId) {
        return  centralWarehouseRepository.findById(centralWarehouseId).map(warehouse -> {
            CentralWarehouseDto warehouseDto = CentralWarehouseMapper.toDto(warehouse);
            return new Result(true, "Central Warehouse retrieved successfully", warehouseDto);
        }).orElseThrow(() -> new CustomException("Central Warehouse not found with ID: " + centralWarehouseId));
    }

    @Override
    public Result deleteCentralWarehouse(String centralWarehouseId) {

        return centralWarehouseRepository.findById(centralWarehouseId).map(warehouse -> {
            centralWarehouseRepository.delete(warehouse);
            return new Result(true, "Central Warehouse deleted successfully");
        })
                .orElseThrow(() -> new CustomException("Central Warehouse not found " ));
    }
}
