package com.cosmetic_manager.cosmetic_manager.repository;

import com.cosmetic_manager.cosmetic_manager.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByCategoryId(int category_id);
    List<Product> findByBrand(String brand);
    List<Product> findByName(String name);
}
