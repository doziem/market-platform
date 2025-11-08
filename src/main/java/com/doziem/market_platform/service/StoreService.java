package com.doziem.market_platform.service;

import com.doziem.market_platform.model.Store;
import com.doziem.market_platform.payload.request.StoreRequest;
import com.doziem.market_platform.payload.response.StoreResponse;
import com.doziem.market_platform.service.impl.UserPrincipal;
import com.doziem.market_platform.system.Result;

import java.io.IOException;
import java.util.List;

public interface StoreService {

    Result createStore(StoreRequest storeRequest, UserPrincipal userPrincipal) throws IOException;
    StoreResponse getStoreByStoreId(String storeId);
    List<StoreResponse> getAllStores();
    Store getStoreByAdmin();
    StoreRequest updateStore(String storeId, StoreRequest storeRequest);
    void deleteStore(String storeId);
    StoreRequest getStoreByEmployee();

}
