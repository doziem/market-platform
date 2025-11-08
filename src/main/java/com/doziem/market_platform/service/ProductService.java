package com.doziem.market_platform.service;

import com.doziem.market_platform.model.User;
import com.doziem.market_platform.payload.dto.ProductDto;
import com.doziem.market_platform.payload.dto.UserDto;

public interface ProductService {

    ProductDto createProduct(ProductDto productDto, User user);
}
