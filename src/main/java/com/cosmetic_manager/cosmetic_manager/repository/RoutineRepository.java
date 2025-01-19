package com.cosmetic_manager.cosmetic_manager.repository;

import com.cosmetic_manager.cosmetic_manager.model.Routine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoutineRepository extends JpaRepository<Routine, Integer> {
    List<Routine> findByUserId(int user_id);
}
