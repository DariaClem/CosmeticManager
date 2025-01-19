package com.cosmetic_manager.cosmetic_manager.utils;

import com.cosmetic_manager.cosmetic_manager.dto.ReviewCreateDto;
import com.cosmetic_manager.cosmetic_manager.dto.ReviewDto;
import com.cosmetic_manager.cosmetic_manager.exceptions.ProductNotFoundException;
import com.cosmetic_manager.cosmetic_manager.exceptions.UserNotFoundException;
import com.cosmetic_manager.cosmetic_manager.model.Product;
import com.cosmetic_manager.cosmetic_manager.model.Review;
import com.cosmetic_manager.cosmetic_manager.model.User;
import com.cosmetic_manager.cosmetic_manager.repository.ProductRepository;
import com.cosmetic_manager.cosmetic_manager.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class ReviewUtil {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public ReviewUtil(UserRepository userRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public Review fromReviewCreateDtoToReview(ReviewCreateDto reviewCreateDto) {
        User user = userRepository
                .findById(reviewCreateDto.getUser_id())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Product product = productRepository
                .findById(reviewCreateDto.getProduct_id())
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        return Review.builder()
                .product(product)
                .user(user)
                .rating(reviewCreateDto.getRating())
                .review(reviewCreateDto.getReview())
                .build();
    }
}
