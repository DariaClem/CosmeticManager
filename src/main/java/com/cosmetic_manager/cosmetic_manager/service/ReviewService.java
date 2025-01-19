package com.cosmetic_manager.cosmetic_manager.service;

import com.cosmetic_manager.cosmetic_manager.dto.ReviewCreateDto;
import com.cosmetic_manager.cosmetic_manager.dto.ReviewDto;
import com.cosmetic_manager.cosmetic_manager.dto.ReviewUpdateDto;
import com.cosmetic_manager.cosmetic_manager.model.Review;
import com.cosmetic_manager.cosmetic_manager.repository.ReviewRepository;
import com.cosmetic_manager.cosmetic_manager.utils.ReviewUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewUtil reviewUtil;

    public ReviewService(ReviewRepository reviewRepository, ReviewUtil reviewUtil) {
        this.reviewRepository = reviewRepository;
        this.reviewUtil = reviewUtil;
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public List<Review> getReviewsByProductId(int product_id) {
        return reviewRepository.findByProductId(product_id);
    }

    public Review createNewReview(ReviewCreateDto reviewDto) {
        return reviewRepository.save(reviewUtil.fromReviewCreateDtoToReview(reviewDto));
    }

    public Review updateReview(ReviewUpdateDto reviewDto) {
        Review review = reviewRepository.findById(reviewDto.getId()).orElseThrow();

        review.setRating(reviewDto.getRating());
        review.setReview(reviewDto.getReview());

        return reviewRepository.save(review);
    }
    public Review deleteReview(int id) {
        Review review = reviewRepository.findById(id).orElseThrow();
        reviewRepository.delete(review);
        return review;
    }

    public List<Review> getReviewsByUserId(int user_id) {
        return reviewRepository.findByUserId(user_id);
    }

    public List<Review> getReviewsByRating(int rating) {
        return reviewRepository.findByRating(rating);
    }
}
