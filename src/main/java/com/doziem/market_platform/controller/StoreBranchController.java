package com.doziem.market_platform.controller;

import com.doziem.market_platform.payload.request.StoreBranchRequest;
import com.doziem.market_platform.payload.request.StoreRequest;
import com.doziem.market_platform.service.StoreBranchService;
import com.doziem.market_platform.service.impl.UserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/store-branch")
@RequiredArgsConstructor
public class StoreBranchController {

    private final StoreBranchService storeBranchService;

    @PostMapping("/{storeId}")
    public ResponseEntity<?> createStore(@PathVariable String storeId, @Valid @RequestBody StoreBranchRequest request) throws IOException {

        return ResponseEntity.status(HttpStatus.CREATED).body(storeBranchService.createBranch(storeId, request));
    }
}
