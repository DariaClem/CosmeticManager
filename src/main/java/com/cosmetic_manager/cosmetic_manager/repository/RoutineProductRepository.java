package com.cosmetic_manager.cosmetic_manager.repository;

import com.cosmetic_manager.cosmetic_manager.model.RoutineProduct;
import com.cosmetic_manager.cosmetic_manager.utils.RoutineProductPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoutineProductRepository extends JpaRepository<RoutineProduct, RoutineProductPK> {
    List<RoutineProduct> findByStepOrder(int order);

    List<RoutineProduct> findById_RoutineId(int routineId);
}