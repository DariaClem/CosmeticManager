package com.cosmetic_manager.cosmetic_manager.service;

import com.cosmetic_manager.cosmetic_manager.dto.ProductDto;
import com.cosmetic_manager.cosmetic_manager.exceptions.CategoryNotFoundException;
import com.cosmetic_manager.cosmetic_manager.exceptions.ProductNotFoundException;
import com.cosmetic_manager.cosmetic_manager.model.Category;
import com.cosmetic_manager.cosmetic_manager.model.Product;
import com.cosmetic_manager.cosmetic_manager.repository.CategoryRepository;
import com.cosmetic_manager.cosmetic_manager.repository.ProductRepository;
import com.cosmetic_manager.cosmetic_manager.utils.ProductUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductUtil productUtil;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, ProductUtil productUtil, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.productUtil = productUtil;
        this.categoryRepository = categoryRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(int id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    public List<Product> getProductsByCategoryId(int category_id) {
        categoryRepository.findById(category_id).orElseThrow(() -> new CategoryNotFoundException("Category not found"));

        return productRepository.findByCategoryId(category_id);
    }

    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    public Product createNewProduct(ProductDto productDto) {
        return productRepository.save(productUtil.fromProductDtoToProduct(productDto));
    }
}
