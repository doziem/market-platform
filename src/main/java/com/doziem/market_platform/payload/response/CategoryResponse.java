package com.doziem.market_platform.payload.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CategoryResponse {

    private String  categoryId;

    private String categoryName;

    private List<ProductResponse> productResponses;
}
