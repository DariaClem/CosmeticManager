package com.cosmetic_manager.cosmetic_manager.controller;

import com.cosmetic_manager.cosmetic_manager.dto.ReviewCreateDto;
import com.cosmetic_manager.cosmetic_manager.dto.ReviewDto;
import com.cosmetic_manager.cosmetic_manager.dto.ReviewUpdateDto;
import com.cosmetic_manager.cosmetic_manager.model.Review;
import com.cosmetic_manager.cosmetic_manager.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@Validated
@Tag(name = "Review API", description = "Operations related to reviews of products")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Operation(summary = "Get all reviews",
            description = "Get all reviews")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reviews found", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Review.class))) }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/get_all")
    public ResponseEntity<List<Review>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @Operation(summary = "Create a new review",
            description = "Create a new review for a product")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Review created successfully", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Review.class)) }),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping("/create_review")
    public ResponseEntity<Review> createReview(@RequestBody @Valid ReviewCreateDto reviewDto) {
        return ResponseEntity.ok(reviewService.createNewReview(reviewDto));
    }

    @Operation(summary = "Update a review",
            description = "Update the rating or review text of a review")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Review updated successfully", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Review.class)) }),
            @ApiResponse(responseCode = "404", description = "Review not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PatchMapping("/update")
    public ResponseEntity<Review> updateReview(@RequestBody @Valid ReviewUpdateDto reviewDto) {
        return ResponseEntity.ok(reviewService.updateReview(reviewDto));
    }

    @Operation(summary = "Get reviews by product id",
            description = "Get reviews for a product by product id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reviews found", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Review.class))) }),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/get_by_product")
    public ResponseEntity<List<Review>> getReviewsByProductId(@Parameter(description = "The ID of the product whose reviews are fetched") @RequestParam int product_id) {
        return ResponseEntity.ok(reviewService.getReviewsByProductId(product_id));
    }

    @Operation(summary = "Delete a review",
            description = "Delete a review")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Review deleted successfully", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Review.class)) }),
            @ApiResponse(responseCode = "404", description = "Review not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @DeleteMapping("/delete")
    public ResponseEntity<Review> deleteReview(@Parameter(description = "The ID of the review to be deleted") @RequestParam int review_id) {
        return ResponseEntity.ok(reviewService.deleteReview(review_id));
    }


}
