package com.cosmetic_manager.cosmetic_manager.service;

import com.cosmetic_manager.cosmetic_manager.dto.ProductDto;
import com.cosmetic_manager.cosmetic_manager.model.Category;
import com.cosmetic_manager.cosmetic_manager.model.Product;
import com.cosmetic_manager.cosmetic_manager.repository.CategoryRepository;
import com.cosmetic_manager.cosmetic_manager.repository.ProductRepository;
import com.cosmetic_manager.cosmetic_manager.utils.CategoryName;
import com.cosmetic_manager.cosmetic_manager.utils.ProductUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductUtil productUtil;

    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("Get all products")
    void getAllProducts() {
        // Arrange
        Category category = new Category(1, CategoryName.MAKEUP);
        Product product1 = new Product(1, "Eyeshadow palette", "Huda beauty", category, "100gr");
        Product product2 = new Product(2, "Setting spray", "Milk Makeup", category, "50ml");

        when(productRepository.findAll()).thenReturn(List.of(product1, product2));

        // Act
        var result = productService.getAllProducts();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(product1, result.get(0));
        assertEquals(product2, result.get(1));

        verify(productRepository, times(1)).findAll();
    }
}
