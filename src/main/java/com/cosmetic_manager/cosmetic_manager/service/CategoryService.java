package com.cosmetic_manager.cosmetic_manager.service;

import com.cosmetic_manager.cosmetic_manager.dto.CategoryDto;
import com.cosmetic_manager.cosmetic_manager.model.Category;
import com.cosmetic_manager.cosmetic_manager.repository.CategoryRepository;
import com.cosmetic_manager.cosmetic_manager.utils.CategoryName;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.cosmetic_manager.cosmetic_manager.utils.CategoryUtil.fromCategoryDtoToCategory;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category createNewCategory(CategoryDto categoryDto) {
        Category category = categoryRepository.findByName(categoryDto.getName());

        if (category != null) {
            return category;
        }

        return categoryRepository.save(fromCategoryDtoToCategory(categoryDto));
    }
}
