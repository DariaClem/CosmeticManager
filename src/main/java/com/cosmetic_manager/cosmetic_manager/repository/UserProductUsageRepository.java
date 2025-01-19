package com.cosmetic_manager.cosmetic_manager.repository;

import com.cosmetic_manager.cosmetic_manager.model.UserProductUsage;
import com.cosmetic_manager.cosmetic_manager.utils.UserProductUsagePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserProductUsageRepository extends JpaRepository<UserProductUsage, UserProductUsagePK> {
    List<UserProductUsage> findById_UserId(int userId);
    List<UserProductUsage> findById_ProductId(int productId);
    Optional<UserProductUsage> findById(UserProductUsagePK id);

    @Query(value = "SELECT * FROM user_product_usage u " +
            "WHERE u.user_id = :userId " +
            "AND u.opening_date IS NOT NULL " +
            "AND DATE_ADD(u.opening_date, INTERVAL u.pao MONTH) < CURRENT_DATE",
            nativeQuery = true)
    List<UserProductUsage> findExpiredProducts(int userId);

    @Query(value = "SELECT * FROM user_product_usage u " +
            "WHERE u.user_id = :userId " +
            "AND u.opening_date IS NOT NULL " +
            "AND DATE_ADD(u.opening_date, INTERVAL u.pao MONTH) < DATE_ADD(CURRENT_DATE, INTERVAL :days DAY)",
            nativeQuery = true)
    List<UserProductUsage> findProductsThatWillExpire(int userId, int days);
}