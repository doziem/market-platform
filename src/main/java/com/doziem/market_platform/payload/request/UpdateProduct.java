package com.doziem.market_platform.payload.request;

import com.doziem.market_platform.model.Category;
import com.doziem.market_platform.model.CentralWarehouse;
import com.doziem.market_platform.model.StateWarehouse;
import com.doziem.market_platform.model.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProduct {
    private String productId;
    private String productName;
    private String sku;
    private String description;
    private Double mrp;
    private Double sellingPrice;
    private String brand;
    private String image;
    private Double unitPrice;
    private Integer quantityInStock;
    private Integer reorderLevel;

    private Category category;
    private CentralWarehouse centralWarehouse;

    private Store store;
    private StateWarehouse stateWarehouse;
}
