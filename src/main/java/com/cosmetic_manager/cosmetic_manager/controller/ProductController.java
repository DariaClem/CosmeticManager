package com.cosmetic_manager.cosmetic_manager.controller;

import com.cosmetic_manager.cosmetic_manager.dto.ProductDto;
import com.cosmetic_manager.cosmetic_manager.model.Product;
import com.cosmetic_manager.cosmetic_manager.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping("/products")
@Validated
@Tag(name = "Product API", description = "Operations related to products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Get all products",
            description = "Get all products")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Products found", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Product.class))) }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/get_all")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @Operation(summary = "Create a new product",
            description = "Add a new product for other users to view and add to their collection")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product created successfully", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)) }),
            @ApiResponse(responseCode = "404", description = "Category not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping("/create_product")
    public ResponseEntity<Product> createProduct(@RequestBody ProductDto productDto) {
        return ResponseEntity.ok(productService.createNewProduct(productDto));
    }

    @Operation(summary = "Get product by category id",
            description = "Get product by category id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product found", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)) }),
            @ApiResponse(responseCode = "404", description = "Category not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content= @Content)
    })
    @GetMapping("/get_by_category")
    public ResponseEntity<List<Product>> getProductByCategory(@Parameter(description = "The ID of the category whose products are fetched") @RequestParam int category_id) {
        return ResponseEntity.ok(productService.getProductsByCategoryId(category_id));
    }

    @Operation(summary = "Get products by brand",
            description = "Get products by brand")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Products found", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content= @Content)
    })
    @GetMapping("/get_by_brand")
    public ResponseEntity<List<Product>> getProductByBrand(@Parameter(description = "The brand of the products to be fetched") @RequestParam String brand) {
        return ResponseEntity.ok(productService.getProductsByBrand(brand));
    }

    @Operation(summary = "Get products by name",
            description = "Get products by name")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Products found", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content= @Content)
    })
    @GetMapping("/get_by_name")
    public ResponseEntity<List<Product>> getProductByName(@Parameter(description = "The name of the products to be fetched") @RequestParam String name) {
        return ResponseEntity.ok(productService.getProductsByName(name));
    }
}
