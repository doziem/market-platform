package com.doziem.market_platform.controller;

import com.doziem.market_platform.payload.request.StoreRequest;
import com.doziem.market_platform.service.StoreService;
import com.doziem.market_platform.service.impl.FileStorageService;
import com.doziem.market_platform.service.impl.UserPrincipal;
import com.doziem.market_platform.system.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;
    private final FileStorageService fileStorageService;

    @PostMapping
    public ResponseEntity<?> createStore(@ModelAttribute @Valid StoreRequest storeRequest, @AuthenticationPrincipal UserPrincipal userPrincipal) throws IOException {

        return ResponseEntity.status(HttpStatus.CREATED).body(storeService.createStore(storeRequest, userPrincipal));
    }

    @GetMapping("/{storeId}")
    public ResponseEntity<Result> getStoreByStoreId(@PathVariable String storeId){
        return ResponseEntity.status(HttpStatus.OK).body(new Result(true,"Fetched Successfully", storeService.getStoreByStoreId(storeId)));
    }

    @GetMapping()
    public ResponseEntity<Result> getAllStore(){
        return ResponseEntity.status(HttpStatus.OK).body(new Result(true,"Fetched Successfully", storeService.getAllStores()));
    }
}
