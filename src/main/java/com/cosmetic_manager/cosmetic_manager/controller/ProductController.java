package com.cosmetic_manager.cosmetic_manager.controller;

import com.cosmetic_manager.cosmetic_manager.dto.ProductDto;
import com.cosmetic_manager.cosmetic_manager.model.Product;
import com.cosmetic_manager.cosmetic_manager.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@Validated
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/get_all")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PostMapping("/create_product")
    public ResponseEntity<Product> createProduct(@RequestBody ProductDto productDto) {
        return ResponseEntity.ok(productService.createNewProduct(productDto));
    }

    @GetMapping("/get_by_category")
    public ResponseEntity<List<Product>> getProductByCategory(int category_id) {
        return ResponseEntity.ok(productService.getProductsByCategoryId(category_id));
    }

    @GetMapping("/get_by_brand")
    public ResponseEntity<List<Product>> getProductByBrand(String brand) {
        return ResponseEntity.ok(productService.getProductsByBrand(brand));
    }

    @GetMapping("/get_by_name")
    public ResponseEntity<List<Product>> getProductByName(String name) {
        return ResponseEntity.ok(productService.getProductsByName(name));
    }
}
