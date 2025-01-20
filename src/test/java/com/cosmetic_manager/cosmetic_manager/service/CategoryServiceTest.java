package com.cosmetic_manager.cosmetic_manager.service;

import com.cosmetic_manager.cosmetic_manager.dto.CategoryDto;
import com.cosmetic_manager.cosmetic_manager.model.Category;
import com.cosmetic_manager.cosmetic_manager.repository.CategoryRepository;
import com.cosmetic_manager.cosmetic_manager.utils.CategoryName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.cosmetic_manager.cosmetic_manager.utils.CategoryUtil.fromCategoryDtoToCategory;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    @DisplayName("Create new category with valid data")
    void createCategory() {
        // Arrange
        CategoryDto categoryDto = new CategoryDto(CategoryName.MAKEUP);
        Category categoryFromDto = fromCategoryDtoToCategory(categoryDto);

        Category savedCategory = new Category(1, CategoryName.MAKEUP);

        when(categoryRepository.save(any(Category.class))).thenReturn(savedCategory);

        // Act
        Category result = categoryService.createNewCategory(categoryDto);

        // Assert
        assertNotNull(result);
        assertEquals(savedCategory.getId(), result.getId());
        assertEquals(savedCategory.getName(), result.getName());
    }

    @Test
    @DisplayName("Get all categories")
    void getAllCategories() {
        // Arrange
        Category category1 = new Category(1, CategoryName.MAKEUP);
        Category category2 = new Category(2, CategoryName.SKINCARE);
        when(categoryRepository.findAll()).thenReturn(List.of(category1, category2));

        // Act
        var result = categoryService.getAllCategories();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());

        verify(categoryRepository).findAll();
    }
}
