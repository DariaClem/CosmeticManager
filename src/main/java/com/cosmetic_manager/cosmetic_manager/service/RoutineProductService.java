package com.cosmetic_manager.cosmetic_manager.service;

import com.cosmetic_manager.cosmetic_manager.dto.RoutineProductDto;
import com.cosmetic_manager.cosmetic_manager.exceptions.ProductNotFoundException;
import com.cosmetic_manager.cosmetic_manager.exceptions.RoutineNotFoundException;
import com.cosmetic_manager.cosmetic_manager.model.Product;
import com.cosmetic_manager.cosmetic_manager.model.Routine;
import com.cosmetic_manager.cosmetic_manager.model.RoutineProduct;
import com.cosmetic_manager.cosmetic_manager.model.UserProductUsage;
import com.cosmetic_manager.cosmetic_manager.repository.RoutineProductRepository;
import com.cosmetic_manager.cosmetic_manager.utils.RoutineProductPK;
import com.cosmetic_manager.cosmetic_manager.utils.RoutineProductUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoutineProductService {
    private final RoutineProductRepository routineProductRepository;
    private final RoutineProductUtil routineProductUtil;
    private final RoutineService routineService;
    private final UserProductUsageService userProductUsageService;
    private final ProductService productService;

    public RoutineProductService(RoutineProductRepository routineProductRepository, RoutineProductUtil routineProductUtil, RoutineService routineService, UserProductUsageService userProductUsageService, ProductService productService) {
        this.routineProductRepository = routineProductRepository;
        this.routineProductUtil = routineProductUtil;
        this.routineService = routineService;
        this.userProductUsageService = userProductUsageService;
        this.productService = productService;
    }

    public RoutineProduct addNewRoutineProduct(RoutineProductDto routineProductDto) {
        return routineProductRepository.save(routineProductUtil.fromRoutineProductDtoToRoutineProduct(routineProductDto));
    }

    public RoutineProduct updateRoutineProduct(RoutineProductDto routineProductDto) {
        return routineProductRepository.save(routineProductUtil.fromRoutineProductDtoToRoutineProduct(routineProductDto));
    }

    public RoutineProduct getRoutineProduct(int routine_id, int product_id) {
        Routine routine = routineService.getRoutineById(routine_id);
        if (routine == null) {
            throw new RoutineNotFoundException("Routine not found");
        }
        int user_id = routine.getUser().getId();

        UserProductUsage userProductUsage = userProductUsageService.getUserProductUsage(user_id, product_id);
        if (userProductUsage == null) {
            throw new ProductNotFoundException("Product not found");
        }
        Product product = productService.getProductById(product_id);

        return routineProductRepository.findById(new RoutineProductPK(routine, product)).orElseThrow();
    }

    public RoutineProduct deleteRoutineProduct(int routine_id, int product_id) {
        RoutineProduct routineProduct = getRoutineProduct(routine_id, product_id);

        routineProductRepository.delete(routineProduct);

        return routineProduct;
    }

    public List<RoutineProduct> getRoutineProductsByRoutineId(int routine_id) {
        return routineProductRepository.findById_RoutineId(routine_id);
    }
}
