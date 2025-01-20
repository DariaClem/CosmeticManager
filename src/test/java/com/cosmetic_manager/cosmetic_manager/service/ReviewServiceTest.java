package com.cosmetic_manager.cosmetic_manager.service;

import com.cosmetic_manager.cosmetic_manager.model.Category;
import com.cosmetic_manager.cosmetic_manager.model.Product;
import com.cosmetic_manager.cosmetic_manager.model.Review;
import com.cosmetic_manager.cosmetic_manager.model.User;
import com.cosmetic_manager.cosmetic_manager.repository.ProductRepository;
import com.cosmetic_manager.cosmetic_manager.repository.ReviewRepository;
import com.cosmetic_manager.cosmetic_manager.utils.CategoryName;
import com.cosmetic_manager.cosmetic_manager.utils.ReviewUtil;
import com.cosmetic_manager.cosmetic_manager.utils.SkinType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {
    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ReviewUtil reviewUtil;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    @DisplayName("Get all reviews")
    void getAllReviews() {
        // Arrange
        Category category = new Category(1, CategoryName.MAKEUP);
        Product product = new Product(1, "Eyeshadow palette", "Huda beauty", category, "100gr");

        User user1 = new User(1, "stefana", "stefana@gmail.com", "Stefana123!", SkinType.OILY, new Date(), new Date());
        User user2 = new User(2, "daria", "daria@gmail.com", "Daria123!", SkinType.NORMAL, new Date(), new Date());

        Review review1 = new Review(1, product, user1, 10, "Great product!", new Date());
        Review review2 = new Review(2, product, user2, 4, "Average product!", new Date());

        when(reviewRepository.findAll()).thenReturn(List.of(review1, review2));

        // Act
        var result = reviewService.getAllReviews();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(review1, result.get(0));
        assertEquals(review2, result.get(1));

        verify(reviewRepository, times(1)).findAll();
    }
}