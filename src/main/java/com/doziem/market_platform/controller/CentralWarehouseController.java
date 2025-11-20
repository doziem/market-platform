package com.doziem.market_platform.controller;

import com.doziem.market_platform.payload.dto.CentralWarehouseDto;
import com.doziem.market_platform.payload.response.CentralWarehouseResponse;
import com.doziem.market_platform.service.warehouse.CentralWarehouseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/central-warehouse")
@RequiredArgsConstructor
public class CentralWarehouseController {

    private final CentralWarehouseService centralWarehouseService;

    @PostMapping
    public ResponseEntity<CentralWarehouseResponse> create(
            @RequestBody @Valid CentralWarehouseDto request) {
        return ResponseEntity.ok(centralWarehouseService.createWarehouse(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CentralWarehouseResponse> getById(@PathVariable String id) {
        return ResponseEntity.ok(centralWarehouseService.getWarehouseById(id));
    }

    @GetMapping
    public ResponseEntity<List<CentralWarehouseResponse>> getAll() {
        return ResponseEntity.ok(centralWarehouseService.getAllWarehouses());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CentralWarehouseResponse> update(@PathVariable String id, @RequestBody @Valid CentralWarehouseDto request) {
        return ResponseEntity.ok(centralWarehouseService.updateWarehouse(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        centralWarehouseService.deleteWarehouse(id);
        return ResponseEntity.ok("Central warehouse deleted successfully");
    }
}
