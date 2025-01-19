package com.cosmetic_manager.cosmetic_manager.controller;

import com.cosmetic_manager.cosmetic_manager.dto.ReviewCreateDto;
import com.cosmetic_manager.cosmetic_manager.dto.ReviewDto;
import com.cosmetic_manager.cosmetic_manager.dto.ReviewUpdateDto;
import com.cosmetic_manager.cosmetic_manager.model.Review;
import com.cosmetic_manager.cosmetic_manager.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@Validated
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/get_all")
    public ResponseEntity<List<Review>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @PostMapping("/create_review")
    public ResponseEntity<Review> createReview(@RequestBody ReviewCreateDto reviewDto) {
        return ResponseEntity.ok(reviewService.createNewReview(reviewDto));
    }

    @PatchMapping("/update")
    public ResponseEntity<Review> updateReview(@RequestBody ReviewUpdateDto reviewDto) {
        return ResponseEntity.ok(reviewService.updateReview(reviewDto));
    }

    @GetMapping("/get_by_product")
    public ResponseEntity<List<Review>> getReviewsByProductId(int product_id) {
        return ResponseEntity.ok(reviewService.getReviewsByProductId(product_id));
    }

    @GetMapping("/get_by_user")
    public ResponseEntity<List<Review>> getReviewsByUserId(int user_id) {
        return ResponseEntity.ok(reviewService.getReviewsByUserId(user_id));
    }

    @GetMapping("/get_by_rating")
    public ResponseEntity<List<Review>> getReviewsByRating(int rating) {
        return ResponseEntity.ok(reviewService.getReviewsByRating(rating));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Review> deleteReview(int review_id) {
        return ResponseEntity.ok(reviewService.deleteReview(review_id));
    }


}
