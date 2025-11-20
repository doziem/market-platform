package com.doziem.market_platform.service.product;

import com.doziem.market_platform.payload.request.ProductRequest;
import com.doziem.market_platform.payload.request.UpdateProduct;
import com.doziem.market_platform.payload.response.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductResponse createProduct(ProductRequest request);

    ProductResponse updateProduct(String productId, UpdateProduct request);

    ProductResponse getProduct(String productId);

    List<ProductResponse> getAllProducts();

    void deleteProduct(String productId);
}
