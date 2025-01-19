package com.cosmetic_manager.cosmetic_manager.controller;

import com.cosmetic_manager.cosmetic_manager.dto.RoutineProductDto;
import com.cosmetic_manager.cosmetic_manager.model.Product;
import com.cosmetic_manager.cosmetic_manager.model.Routine;
import com.cosmetic_manager.cosmetic_manager.model.RoutineProduct;
import com.cosmetic_manager.cosmetic_manager.model.UserProductUsage;
import com.cosmetic_manager.cosmetic_manager.service.ProductService;
import com.cosmetic_manager.cosmetic_manager.service.RoutineProductService;
import com.cosmetic_manager.cosmetic_manager.service.RoutineService;
import com.cosmetic_manager.cosmetic_manager.service.UserProductUsageService;
import com.cosmetic_manager.cosmetic_manager.utils.RoutineProductPK;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/routine_product")
@Validated
public class RoutineProductController {
    private final RoutineProductService routineProductService;
    private final RoutineService routineService;
    private final UserProductUsageService userProductUsageService;
    private final ProductService productService;

    public RoutineProductController(RoutineProductService routineProductService, RoutineService routineService,
                                    UserProductUsageService userProductUsageService, ProductService productService) {
        this.routineProductService = routineProductService;
        this.routineService = routineService;
        this.userProductUsageService = userProductUsageService;
        this.productService = productService;
    }

    @GetMapping("/get_all")
    public ResponseEntity<List<RoutineProduct>> getAllRoutineProducts(int routine_id) {
        return ResponseEntity.ok(routineProductService.getRoutineProductsByRoutineId(routine_id));
    }

    @PostMapping("/add_product")
    public ResponseEntity<RoutineProduct> addProductToRoutine(@RequestBody RoutineProductDto routineProductDto) {
        return ResponseEntity.ok(routineProductService.addNewRoutineProduct(routineProductDto));
    }

    @PatchMapping("/edit_product")
    public ResponseEntity<RoutineProduct> editRoutineProduct(@RequestBody RoutineProductDto routineProductDto) {
        return ResponseEntity.ok(routineProductService.updateRoutineProduct(routineProductDto));
    }

    @DeleteMapping("/delete_product")
    public ResponseEntity<RoutineProduct> deleteRoutineProduct(int routine_id, int product_id) {
        return ResponseEntity.ok(routineProductService.deleteRoutineProduct(routine_id, product_id));
    }
}
