package com.cosmetic_manager.cosmetic_manager.controller;

import com.cosmetic_manager.cosmetic_manager.dto.ReviewCreateDto;
import com.cosmetic_manager.cosmetic_manager.dto.ReviewUpdateDto;
import com.cosmetic_manager.cosmetic_manager.model.Category;
import com.cosmetic_manager.cosmetic_manager.model.Product;
import com.cosmetic_manager.cosmetic_manager.model.Review;
import com.cosmetic_manager.cosmetic_manager.model.User;
import com.cosmetic_manager.cosmetic_manager.service.ReviewService;
import com.cosmetic_manager.cosmetic_manager.utils.CategoryName;
import com.cosmetic_manager.cosmetic_manager.utils.SkinType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ReviewController.class)
public class ReviewControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private ReviewService reviewService;

    private User user1;
    private User user2;
    private Review review1;
    private Review review2;
    private Review review3;
    private ReviewCreateDto reviewCreateDto;
    private ReviewCreateDto reviewCreateDto2;
    private ReviewUpdateDto reviewUpdateDto;
    private ReviewUpdateDto reviewUpdateDto2;
    private List<Review> reviews;

    @BeforeEach
    public void setUp() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = dateFormat.parse("1999-12-12");

        user1 = new User(1, "stefana", "stefana@gmail.com", "Stefana123!", SkinType.OILY, birthDate, new Date());
        user2 = new User(2, "daria", "daria@gmail.com", "Daria123!", SkinType.DRY, birthDate, new Date());

        Category category = new Category(1, CategoryName.MAKEUP);

        Product product = new Product(1, "Eyeshadow palette", "Huda beauty", category, "100gr");

        review1 = new Review(1, product, user1, 10, "Great product", new Date());
        review2 = new Review(2, product, user1, 4, "Good product", new Date());
        review3 = new Review(3, product, user2, 1, "Awful product", new Date());

        reviewCreateDto = new ReviewCreateDto(1, user1.getId(), 10, "Great product");
        reviewCreateDto2 = new ReviewCreateDto(1, user1.getId(), 5, "Bad and expensive product");

        reviewUpdateDto = new ReviewUpdateDto(2, 9, "Good product");

        reviews = List.of(review1, review2, review3);
    }

    @Test
    public void getAllReviews() throws Exception {
        when(reviewService.getAllReviews()).thenReturn(reviews);

        mockMvc.perform(get("/reviews/get_all")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].rating").value(review1.getRating()))
                .andExpect(jsonPath("$[1].review").value(review2.getReview()))
                .andExpect(jsonPath("$[2].user.username").value(user2.getUsername()));
    }

    @Test
    public void createReview() throws Exception {
        when(reviewService.createNewReview(any())).thenReturn(review1);

        mockMvc.perform(post("/reviews/create_review")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(reviewCreateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rating").value(reviewCreateDto.getRating()))
                .andExpect(jsonPath("$.review").value(reviewCreateDto.getReview()));
    }

    @Test
    public void updateReview() throws Exception {
        Review review = reviews.get(2);
        review.setReview(reviewUpdateDto.getReview());
        review.setRating(reviewUpdateDto.getRating());

        when(reviewService.updateReview(any())).thenReturn(review);

        mockMvc.perform(patch("/reviews/update")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(reviewUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rating").value(reviewUpdateDto.getRating()))
                .andExpect(jsonPath("$.review").value(reviewUpdateDto.getReview()));
    }

    @Test
    public void deleteReview() throws Exception {
        when(reviewService.deleteReview(3)).thenReturn(reviews.get(2));

        mockMvc.perform(delete("/reviews/delete?review_id=3")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rating").value(review3.getRating()));
    }

    @Test
    public void getReviewByProductId() throws Exception {
        when(reviewService.getReviewsByProductId(1)).thenReturn(reviews);

        mockMvc.perform(get("/reviews/get_by_product?product_id=1")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].rating").value(review1.getRating()))
                .andExpect(jsonPath("$[1].review").value(review2.getReview()))
                .andExpect(jsonPath("$[2].user.username").value(user2.getUsername()));
    }
}
