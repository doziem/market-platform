package com.doziem.market_platform.service.product;

import com.doziem.market_platform.enums.RequestStatus;
import com.doziem.market_platform.exception.CustomException;
import com.doziem.market_platform.exception.ResourceNotFoundException;
import com.doziem.market_platform.mapper.ProductMapper;
import com.doziem.market_platform.model.*;
import com.doziem.market_platform.payload.request.ProductRequest;
import com.doziem.market_platform.payload.request.ProductVariantRequest;
import com.doziem.market_platform.payload.request.UpdateProduct;
import com.doziem.market_platform.payload.response.ProductResponse;
import com.doziem.market_platform.repository.*;
import com.doziem.market_platform.service.cloudinary.CloudinaryService;
import com.doziem.market_platform.service.impl.UserPrincipal;
import com.doziem.market_platform.system.Result;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CentralWarehouseRepository centralWarehouseRepository;
    private final CloudinaryService cloudinaryService;
    private final StateReplenishmentRequestRepository requestRepository;
    private final StoreRepository storeRepository;
    private final ProductVariantRepository productVariantRepository;

    @Override
    @Transactional
    public ProductResponse createProduct(ProductRequest request) {
        if (request == null || request.getCentralWarehouseId() == null
                || request.getCentralWarehouseId().isBlank()) {
            throw new CustomException("Central warehouse is required");
        }
        return createProducts(request);
    }

    @Override
    @Transactional
    public ProductResponse createProducts(ProductRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Product request cannot be null");
        }

        validateCreateProductRequest(request);

        String categoryId = requireText(request.getCategoryId(), "Category is required");
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + categoryId));

        Store store = null;
        if (request.getStoreId() != null && !request.getStoreId().isBlank()) {
            String storeId = request.getStoreId().trim();
            store = storeRepository.findById(storeId)
                    .orElseThrow(() -> new ResourceNotFoundException("Store not found: " + storeId));
        }

        CentralWarehouse centralWarehouse = null;
        if (request.getCentralWarehouseId() != null
                && !request.getCentralWarehouseId().isBlank()) {
            String warehouseId = request.getCentralWarehouseId().trim();
            centralWarehouse = centralWarehouseRepository.findById(warehouseId)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Central warehouse not found: " + warehouseId));
        }

        Product product = ProductMapper.toEntity(request, category, centralWarehouse);
        product.setStore(store);

        product.setImages(uploadProductImages(request.getImages(), product));

        return ProductMapper.toResponse(productRepository.save(product));
    }

    private void validateCreateProductRequest(ProductRequest request) {
        requireText(request.getProductName(), "Product name is required");
        requireText(request.getDescription(), "Product description is required");
        requireText(request.getBrand(), "Product brand is required");
        requireText(request.getCategoryId(), "Category is required");

        if (request.getImages() == null || request.getImages().size() < 4) {
            throw new IllegalArgumentException("Product must have at least 4 images");
        }
        if (request.getImages().stream().anyMatch(file -> file == null || file.isEmpty())) {
            throw new CustomException("Product images cannot be empty");
        }

        List<ProductVariantRequest> variants = request.getVariants() == null
                ? List.of()
                : request.getVariants();
        if (variants.isEmpty()) {
            validatePrices(request.getSellingPrice(), request.getCostPrice(), request.getMrp(), "Product");
            return;
        }

        Set<String> skus = new HashSet<>();
        Set<List<String>> colorSizes = new HashSet<>();
        for (ProductVariantRequest variant : variants) {
            if (variant == null) {
                throw new CustomException("Product variant cannot be null");
            }

            String sku = requireText(variant.getSku(), "Variant SKU is required");
            if (!skus.add(sku.toLowerCase(Locale.ROOT))) {
                throw new CustomException("Duplicate SKU in request: " + sku);
            }
            if (productVariantRepository.existsBySku(sku)) {
                throw new IllegalArgumentException("SKU already exists: " + sku);
            }

            String color = requireText(variant.getColor(), "Variant color is required");
            String size = requireText(variant.getSize(), "Variant size is required");
            List<String> colorSize = List.of(
                    color.toLowerCase(Locale.ROOT),
                    size.toLowerCase(Locale.ROOT));
            if (!colorSizes.add(colorSize)) {
                throw new IllegalArgumentException(
                        "Duplicate color and size combination in request: " + color + " / " + size);
            }

            validatePrices(variant.getSellingPrice(), variant.getCostPrice(), variant.getMrp(), "Variant");
        }
    }

    private void validatePrices(
            BigDecimal sellingPrice,
            BigDecimal costPrice,
            BigDecimal mrp,
            String priceOwner) {
        if (sellingPrice == null || sellingPrice.signum() <= 0) {
            throw new IllegalArgumentException(
                    priceOwner + " selling price is required and must be greater than zero");
        }
        if ((costPrice != null && costPrice.signum() < 0)
                || (mrp != null && mrp.signum() < 0)) {
            throw new IllegalArgumentException(priceOwner + " cost price and MRP cannot be negative");
        }
    }

    private List<ProductImage> uploadProductImages(List<MultipartFile> files, Product product) {
        List<ProductImage> images = new ArrayList<>(files.size());
        for (MultipartFile file : files) {
            Map<String, String> upload = cloudinaryService.upload(file, "products");
            images.add(ProductImage.builder()
                    .imageUrl(upload.get("url"))
                    .publicId(upload.get("publicId"))
                    .product(product)
                    .build());
        }
        return images;
    }

    private String requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
        return value.trim();
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
        return ProductMapper.toDetailedResponseList(productRepository.findAll());
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
    @Transactional
    public Result assignProductsToCentralWarehouse(String warehouseId, List<String> productIds) {
        try {
            CentralWarehouse warehouse = centralWarehouseRepository.findById(warehouseId)
                    .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found"));

            List<Product> products = productRepository.findAllById(productIds);

            products.forEach(warehouse::addProduct);

         return new Result(true, "Product successfully added to Central Warehouse", centralWarehouseRepository.save(warehouse));
        }catch (CustomException ex){
            log.error("Error Adding Product to Central warehouse {}", ex.getMessage());
            return new Result(false, "Error Adding Product to Central warehouse");
        }

    }

    @Override
    @Transactional
    public void fulfillStateReplenishmentRequest(String requestId, UserPrincipal currentUser) {;

        StateReplenishmentRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new CustomException("Request not found"));

        StateWarehouse stateWarehouse = request.getStateWarehouse();
        CentralWarehouse centralWarehouse = stateWarehouse.getCentralWarehouse();

        if (centralWarehouse == null) {
            throw new CustomException("State warehouse is not linked to a central warehouse");
        }

        // Verify all requested products exist & have enough stock
        for (StateReplenishmentItem item : request.getItems()) {
            Product product = item.getProduct();

            if (product.getCentralWarehouse() == null || !product.getCentralWarehouse().getCentralWarehouseId().equals(centralWarehouse.getCentralWarehouseId())) {
                throw new CustomException(
                        "Product " + product.getProductName() + " is not assigned to the central warehouse");
            }

            if (product.getQuantityInStock() < item.getRequestedQuantity()) {
                throw new CustomException("Insufficient stock for product " + product.getProductName());
            }
        }

        // All items validated. stock deductions and transfers.
        for (StateReplenishmentItem item : request.getItems()) {

            Product product = validateProductAndStockQuantity(item, stateWarehouse);

            productRepository.save(product);
        }

        // Update request status
        request.setStatus(RequestStatus.FULFILLED);
        request.setFulfilledDate(ZonedDateTime.now());
        request.setApprovedBy(currentUser.getStaff().getFirstName() + " " + currentUser.getStaff().getLastName());
        request.setApprovedByPosition(currentUser.getStaff().getPosition());

        requestRepository.save(request);

    }

    private static Product validateProductAndStockQuantity(StateReplenishmentItem item, StateWarehouse stateWarehouse) {
        Product product = item.getProduct();
        int requestedQty = item.getRequestedQuantity();

        product.setQuantityInStock(product.getQuantityInStock() - requestedQty);
        stateWarehouse.addProduct(product);
        return product;
    }


}
