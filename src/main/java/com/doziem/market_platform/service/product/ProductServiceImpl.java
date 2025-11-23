package com.doziem.market_platform.service.product;

import com.doziem.market_platform.exception.CustomException;
import com.doziem.market_platform.exception.ResourceNotFoundException;
import com.doziem.market_platform.mapper.CentralWarehouseMapper;
import com.doziem.market_platform.mapper.ProductMapper;
import com.doziem.market_platform.model.Category;
import com.doziem.market_platform.model.CentralWarehouse;
import com.doziem.market_platform.model.Product;
import com.doziem.market_platform.model.ProductImage;
import com.doziem.market_platform.payload.request.ProductRequest;
import com.doziem.market_platform.payload.request.UpdateProduct;
import com.doziem.market_platform.payload.response.ProductResponse;
import com.doziem.market_platform.repository.*;
import com.doziem.market_platform.service.cloudinary.CloudinaryService;
import com.doziem.market_platform.system.Result;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CentralWarehouseRepository centralWarehouseRepository;
    private final CloudinaryService cloudinaryService;

    @Override
    public ProductResponse createProduct(ProductRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        CentralWarehouse centralWarehouse = centralWarehouseRepository.findById(request.getCentralWarehouseId())
                .orElseThrow(() -> new ResourceNotFoundException("Central Warehouse not found"));

        Product product = ProductMapper.toEntity(request,category,centralWarehouse);

        Product validatedProduct = validateProduct(product,request);

        Product savedProduct = productRepository.save(validatedProduct);

        return ProductMapper.toResponse(productRepository.save(savedProduct));

    }

    @Override
    @Transactional
    public ProductResponse updateProduct(String productId, UpdateProduct request) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        Product updateProduct = ProductMapper.toUpdateEntity(existingProduct, request);


        if (request.getNewImages() != null && !request.getNewImages().isEmpty()) {

            if (request.getNewImages().size() < 4) {
                throw new IllegalArgumentException("Product must have at least 4 images");
            }

            // delete old images from Cloudinary
            for (ProductImage img : existingProduct.getImages()) {
                cloudinaryService.delete(img.getPublicId());
            }

            existingProduct.getImages().clear();

            // upload new images
            List<ProductImage> newImageList = new ArrayList<>();

            for (MultipartFile file : request.getNewImages()) {
                Map<String, String> upload = cloudinaryService.upload(file, "products");

                ProductImage image = ProductImage.builder()
                        .imageUrl(upload.get("url"))
                        .publicId(upload.get("publicId"))
                        .product(existingProduct)
                        .build();

                newImageList.add(image);
            }
            updateProduct.setImages(newImageList);
        }

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

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException("Product not found"));

        // delete cloudinary images
        product.getImages().forEach(img -> {
            cloudinaryService.delete(img.getPublicId());
        });
        productRepository.deleteById(productId);
    }

    @Override
    public Result assignProductsToCentralWarehouse(String warehouseId, List<String> productIds) {
        try {
            CentralWarehouse warehouse = centralWarehouseRepository.findById(warehouseId)
                    .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found"));

            List<Product> products = productRepository.findAllById(productIds);

            products.forEach(warehouse::addProduct);

         return new Result(true, "Product successfully added to Central warehouse", centralWarehouseRepository.save(warehouse));
        }catch (CustomException ex){
            log.error("Error Adding Product to Central warehouse {}", ex.getMessage());
            return new Result(false, "Error Adding Product to Central warehouse");
        }

    }

    private Product validateProduct(Product product, ProductRequest request) {

        if (product.getImages().size() < 4) {
            throw new CustomException("Product must have at least 4 images");
        }

        List<ProductImage> imageList = new ArrayList<>();

        for (MultipartFile file : request.getImages()) {

            Map<String, String> upload = cloudinaryService.upload(file, "products");

            ProductImage img = new ProductImage();
            img.setImageUrl(upload.get("url"));
            img.setPublicId(upload.get("publicId"));
            img.setProduct(product);

            imageList.add(img);
        }

        product.setImages(imageList);

        return product;
    }
}
