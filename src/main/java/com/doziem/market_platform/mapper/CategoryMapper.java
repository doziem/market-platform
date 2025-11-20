package com.doziem.market_platform.mapper;

import com.doziem.market_platform.model.Category;
import com.doziem.market_platform.payload.response.CategoryResponse;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public static Category toEntity(String categoryName) {
        if (categoryName == null) {
            return null;
        }
        return Category.builder()
                .categoryName(categoryName)
                .build();
    }

    public static CategoryResponse toResponse(Category category) {
        if (category == null) {
            return null;
        }

    return CategoryResponse.builder()
            .categoryId(category.getCategoryId())
            .categoryName(category.getCategoryName())
            .productResponses(ProductMapper.toResponseList(category.getProducts()))
            .build();
    }

}
