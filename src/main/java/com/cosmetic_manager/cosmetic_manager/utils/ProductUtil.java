package com.cosmetic_manager.cosmetic_manager.utils;

import com.cosmetic_manager.cosmetic_manager.dto.ProductDto;
import com.cosmetic_manager.cosmetic_manager.exceptions.CategoryNotFoundException;
import com.cosmetic_manager.cosmetic_manager.model.Category;
import com.cosmetic_manager.cosmetic_manager.model.Product;
import com.cosmetic_manager.cosmetic_manager.repository.CategoryRepository;
import org.springframework.stereotype.Component;

@Component
public class ProductUtil {
    private final CategoryRepository categoryRepository;

    public ProductUtil(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Product fromProductDtoToProduct(ProductDto productDto) {
        Category category = categoryRepository
                .findById(productDto.getCategory_id())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));

        return Product.builder()
                .name(productDto.getName())
                .brand(productDto.getBrand())
                .category(category)
                .quantity(productDto.getQuantity())
                .build();
    }
}
