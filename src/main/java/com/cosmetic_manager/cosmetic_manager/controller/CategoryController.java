package com.cosmetic_manager.cosmetic_manager.controller;

import com.cosmetic_manager.cosmetic_manager.dto.CategoryDto;
import com.cosmetic_manager.cosmetic_manager.model.Category;
import com.cosmetic_manager.cosmetic_manager.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@Validated
@Tag(name = "Category API", description = "Operations related to categories of products")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "Get all categories",
            description = "Get all categories of products")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categories found", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Category.class))) }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/get_all")
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @Operation(summary = "Create a new category",
            description = "Create a new category for a product")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Category created successfully", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Category.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping("/create_category")
    public ResponseEntity<Category> createCategory(@RequestBody CategoryDto categoryDto) {
        return ResponseEntity.ok(categoryService.createNewCategory(categoryDto));
    }
}