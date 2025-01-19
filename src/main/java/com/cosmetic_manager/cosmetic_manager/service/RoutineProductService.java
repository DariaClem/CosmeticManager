package com.cosmetic_manager.cosmetic_manager.service;

import com.cosmetic_manager.cosmetic_manager.dto.RoutineProductDto;
import com.cosmetic_manager.cosmetic_manager.exceptions.ProductNotFoundException;
import com.cosmetic_manager.cosmetic_manager.exceptions.RoutineNotFoundException;
import com.cosmetic_manager.cosmetic_manager.model.*;
import com.cosmetic_manager.cosmetic_manager.repository.ProductRepository;
import com.cosmetic_manager.cosmetic_manager.repository.RoutineProductRepository;
import com.cosmetic_manager.cosmetic_manager.repository.RoutineRepository;
import com.cosmetic_manager.cosmetic_manager.repository.UserProductUsageRepository;
import com.cosmetic_manager.cosmetic_manager.utils.RoutineProductPK;
import com.cosmetic_manager.cosmetic_manager.utils.RoutineProductUtil;
import com.cosmetic_manager.cosmetic_manager.utils.UserProductUsagePK;
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
    private final RoutineRepository routineRepository;
    private final UserProductUsageRepository userProductUsageRepository;

    public RoutineProductService(RoutineProductRepository routineProductRepository, RoutineProductUtil routineProductUtil, RoutineService routineService, UserProductUsageService userProductUsageService, ProductService productService, RoutineRepository routineRepository, UserProductUsageRepository userProductUsageRepository) {
        this.routineProductRepository = routineProductRepository;
        this.routineProductUtil = routineProductUtil;
        this.routineService = routineService;
        this.userProductUsageService = userProductUsageService;
        this.productService = productService;
        this.routineRepository = routineRepository;
        this.userProductUsageRepository = userProductUsageRepository;
    }

    public RoutineProduct addNewRoutineProduct(RoutineProductDto routineProductDto) {
        Routine routine = routineRepository.findById(routineProductDto.getRoutineId()).orElseThrow(() -> new RoutineNotFoundException("Routine not found"));
        User user = routine.getUser();
        UserProductUsage userProductUsage = userProductUsageRepository.findById(new UserProductUsagePK(user, productService.getProductById(routineProductDto.getProductId()))).orElseThrow(() -> new ProductNotFoundException("Product not found"));

        RoutineProduct routineProduct = routineProductRepository.findById(new RoutineProductPK(routine,
                productService.getProductById(routineProductDto.getProductId()))).orElse(null);

        if (routineProduct != null) {
            return routineProduct;
        }

        return routineProductRepository.save(routineProductUtil.fromRoutineProductDtoToRoutineProduct(routineProductDto));
    }

    public RoutineProduct updateRoutineProduct(RoutineProductDto routineProductDto) {
        Routine routine = routineRepository.findById(routineProductDto.getRoutineId()).orElseThrow(() -> new RoutineNotFoundException("Routine not found"));
        User user = routine.getUser();
        UserProductUsage userProductUsage = userProductUsageRepository.findById(new UserProductUsagePK(user, productService.getProductById(routineProductDto.getProductId()))).orElseThrow(() -> new ProductNotFoundException("Product not found"));

        getRoutineProduct(routineProductDto.getRoutineId(), routineProductDto.getProductId());

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

        return routineProductRepository.findById(new RoutineProductPK(routine, product)).orElseThrow(() -> new ProductNotFoundException("Product not found in routine"));
    }

    public RoutineProduct deleteRoutineProduct(int routine_id, int product_id) {
        RoutineProduct routineProduct = getRoutineProduct(routine_id, product_id);

        System.out.println(routineProduct);
        routineProductRepository.delete(routineProduct);

        return routineProduct;
    }

    public List<RoutineProduct> getRoutineProductsByRoutineId(int routine_id) {
        routineRepository.findById(routine_id).orElseThrow(() -> new RoutineNotFoundException("Routine not found"));

        return routineProductRepository.findById_RoutineId(routine_id);
    }
}
