package com.doziem.market_platform.service.impl;

import com.doziem.market_platform.exception.CustomException;
import com.doziem.market_platform.mapper.StoreMapper;
import com.doziem.market_platform.model.Store;
import com.doziem.market_platform.model.User;
import com.doziem.market_platform.payload.dto.UserDto;
import com.doziem.market_platform.payload.dto.WorkHourDto;
import com.doziem.market_platform.payload.request.StoreRequest;
import com.doziem.market_platform.payload.response.StoreResponse;
import com.doziem.market_platform.repository.StoreRepository;
import com.doziem.market_platform.service.StoreService;
import com.doziem.market_platform.system.ImageValidator;
import com.doziem.market_platform.system.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final ImageValidator imageValidator;
    private final FileStorageService fileStorageService;

    @Override
    public Result createStore(StoreRequest storeRequest, UserPrincipal userPrincipal) throws IOException {

        if( storeRepository.existsByStoreName(storeRequest.getStoreName().trim())){
            throw  new CustomException("Store with name "  + storeRequest.getStoreName() + " exist already");
        }

        String logoFilename = processAndStoreLogo(storeRequest.getStoreLogo());

        Store store = buildStoreEntity(storeRequest, logoFilename, userPrincipal);

        Store savedStore = storeRepository.save(store);

        return new Result(true,"Successfully Created", StoreMapper.storeResponse(savedStore));
    }

    @Override
    public StoreResponse getStoreByStoreId(String storeId) {
        Store singleStore = storeRepository.findById(storeId).orElseThrow(()->new CustomException("Store not found"));
        return  StoreMapper.storeResponse(singleStore);
     }

    @Override
    public List<StoreResponse> getAllStores() {
        try {
           return storeRepository.findAll().stream().map(StoreMapper::storeResponse).toList();
        }catch (CustomException ex){
//            log("Error {}", ex.getMessage());
            throw new CustomException("Error Fetching Stores");
        }

    }

    @Override
    public Store getStoreByAdmin() {
        return null;
    }

    @Override
    public StoreRequest updateStore(String storeId, StoreRequest storeRequest) {
        return null;
    }

    @Override
    public void deleteStore(String storeId) {

    }

    @Override
    public StoreRequest getStoreByEmployee() {
        return null;
    }

    /**
     * Processes and stores logo file
     */
    private String processAndStoreLogo(MultipartFile logoFile) throws IOException {
        if (logoFile == null || logoFile.isEmpty()) {
            return null;
        }
        imageValidator.validateImage(logoFile);
        return fileStorageService.storeFile(logoFile);
    }

    /**
     * Builds Store entity with logo filename
     */
    private Store buildStoreEntity(StoreRequest storeRequest, String logoFilename,UserPrincipal userPrincipal) {
        User user = userPrincipal.getUser();
        Store store = StoreMapper.toEntity(storeRequest,user);
        store.setUser(user);
        store.setStoreLogo(logoFilename);
        return store;
    }


}
