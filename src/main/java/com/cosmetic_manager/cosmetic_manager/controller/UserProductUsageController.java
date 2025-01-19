package com.cosmetic_manager.cosmetic_manager.controller;

import com.cosmetic_manager.cosmetic_manager.dto.UserProductUsageDto;
import com.cosmetic_manager.cosmetic_manager.model.Product;
import com.cosmetic_manager.cosmetic_manager.model.User;
import com.cosmetic_manager.cosmetic_manager.model.UserProductUsage;
import com.cosmetic_manager.cosmetic_manager.service.ProductService;
import com.cosmetic_manager.cosmetic_manager.service.UserProductUsageService;
import com.cosmetic_manager.cosmetic_manager.service.UserService;
import com.cosmetic_manager.cosmetic_manager.utils.UserProductUsagePK;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user_product_usage")
@Validated
public class UserProductUsageController {
    private final UserProductUsageService userProductUsageService;


    public UserProductUsageController(UserProductUsageService userProductUsageService) {
        this.userProductUsageService = userProductUsageService;
    }

    @GetMapping("/get_all")
    public ResponseEntity<List<UserProductUsage>> getAllUserProductUsage(int user_id) {
        return ResponseEntity.ok(userProductUsageService.getAllUserProductUsages(user_id));
    }

    @GetMapping("/get_by_product")
    public ResponseEntity<UserProductUsage> getUserProductUsageByProductId(int user_id, int product_id) {
        return ResponseEntity.ok(userProductUsageService.getUserProductUsage(user_id, product_id));
    }

    @PostMapping("/add_product_usage")
    public ResponseEntity<UserProductUsage> addUserProductUsage(@RequestBody UserProductUsageDto userProductUsageDto) {
        return ResponseEntity.ok(userProductUsageService.addUserProductUsage(userProductUsageDto));
    }

    @PatchMapping("/increase_usage")
    public ResponseEntity<UserProductUsage> increaseProductUsage(int user_id, int product_id) {
        return ResponseEntity.ok(userProductUsageService.increaseProductUsage(user_id, product_id));
    }

    @PatchMapping("/edit_product_usage")
    public ResponseEntity<UserProductUsage> editUserProductUsage(@RequestBody UserProductUsageDto userProductUsageDto) {
        return ResponseEntity.ok(userProductUsageService.editUserProductUsage(userProductUsageDto));
    }

    @DeleteMapping("/delete_product_usage")
    public ResponseEntity<UserProductUsage> deleteUserProductUsage(int user_id, int product_id) {
        return ResponseEntity.ok(userProductUsageService.deleteUserProductUsage(user_id, product_id));
    }

    @GetMapping("/get_expired")
    public ResponseEntity<List<UserProductUsage>> getExpiredProducts(int user_id) {
        return ResponseEntity.ok(userProductUsageService.getExpiredProducts(user_id));
    }

    @GetMapping("/get_expiring_soon")
    public ResponseEntity<List<UserProductUsage>> getProductsThatWillExpire(int user_id, int days) {
        return ResponseEntity.ok(userProductUsageService.getProductsThatWillExpire(user_id, days));
    }

}
