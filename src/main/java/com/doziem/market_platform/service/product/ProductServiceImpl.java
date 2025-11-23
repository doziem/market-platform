package com.doziem.market_platform.service.product;

import com.doziem.market_platform.exception.ResourceNotFoundException;
import com.doziem.market_platform.mapper.ProductMapper;
import com.doziem.market_platform.model.Category;
import com.doziem.market_platform.model.CentralWarehouse;
import com.doziem.market_platform.model.Product;
import com.doziem.market_platform.payload.request.ProductRequest;
import com.doziem.market_platform.payload.request.UpdateProduct;
import com.doziem.market_platform.payload.response.ProductResponse;
import com.doziem.market_platform.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CentralWarehouseRepository centralWarehouseRepository;

    @Override
    public ProductResponse createProduct(ProductRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        CentralWarehouse centralWarehouse = centralWarehouseRepository.findById(request.getCentralWarehouseId())
                .orElseThrow(() -> new ResourceNotFoundException("Central Warehouse not found"));

        Product product = ProductMapper.toEntity(request,category,centralWarehouse);
        Product validatedProduct = validateProduct(product);
        Product savedProduct = productRepository.save(validatedProduct);

        return ProductMapper.toResponse(productRepository.save(savedProduct));

    }


    @Override
    public ProductResponse updateProduct(String productId, UpdateProduct request) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        Product updateProduct = ProductMapper.toUpdateEntity(existingProduct, request);

        return  ProductMapper.toResponse(productRepository.save(updateProduct));
    }

    @Override
    public ProductResponse getProduct(String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return ProductMapper.toResponse(product);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return ProductMapper.toResponseList(productRepository.findAll());
    }

    @Override
    public void deleteProduct(String productId) {
        if (!productRepository.existsById(productId)) {
            throw new ResourceNotFoundException("Product not found");
        }
        productRepository.deleteById(productId);
    }

    private Product validateProduct(Product product) {
        return product;
    }
}
