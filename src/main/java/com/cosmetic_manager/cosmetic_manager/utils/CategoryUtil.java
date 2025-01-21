package com.cosmetic_manager.cosmetic_manager.utils;

import com.cosmetic_manager.cosmetic_manager.dto.CategoryDto;
import com.cosmetic_manager.cosmetic_manager.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryUtil {
    public static Category fromCategoryDtoToCategory(CategoryDto categoryDto) {
        return Category.builder()
                .name(categoryDto.getName())
                .build();
    }
}
