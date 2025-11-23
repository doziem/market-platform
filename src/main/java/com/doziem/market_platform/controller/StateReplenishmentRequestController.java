package com.doziem.market_platform.controller;

import com.doziem.market_platform.payload.dto.StateReplenishmentRequestDto;
import com.doziem.market_platform.payload.response.StateReplenishmentRequestResponse;
import com.doziem.market_platform.service.warehouse.StateReplenishmentRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/state-replenishment")
@RequiredArgsConstructor
public class StateReplenishmentRequestController {

    private final StateReplenishmentRequestService requestService;

    @PostMapping
    public ResponseEntity<StateReplenishmentRequestResponse> createRequest(@RequestBody @Valid StateReplenishmentRequestDto dto) {
        return ResponseEntity.ok(requestService.createRequest(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StateReplenishmentRequestResponse> getRequest(@PathVariable String id) {
        return ResponseEntity.ok(requestService.getRequestById(id));
    }

    @GetMapping
    public ResponseEntity<List<StateReplenishmentRequestResponse>> getAllRequests() {
        return ResponseEntity.ok(requestService.getAllRequests());
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<StateReplenishmentRequestResponse> approve(@PathVariable String id, @RequestParam String approvedBy) {
        return ResponseEntity.ok(requestService.approveRequest(id, approvedBy));
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<StateReplenishmentRequestResponse> reject(@PathVariable String id, @RequestParam String approvedBy) {
        return ResponseEntity.ok(requestService.rejectRequest(id, approvedBy));
    }
}
