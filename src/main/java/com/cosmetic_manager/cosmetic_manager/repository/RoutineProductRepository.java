package com.cosmetic_manager.cosmetic_manager.repository;

import com.cosmetic_manager.cosmetic_manager.model.RoutineProduct;
import com.cosmetic_manager.cosmetic_manager.utils.RoutineProductPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoutineProductRepository extends JpaRepository<RoutineProduct, RoutineProductPK> {
    List<RoutineProduct> findById_RoutineId(int routineId);

    void deleteRoutineProductsById_RoutineId(int routineId);
    void deleteRoutineProductsById_ProductId(int productId);
}