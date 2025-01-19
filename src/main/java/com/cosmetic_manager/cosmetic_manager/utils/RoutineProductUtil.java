package com.cosmetic_manager.cosmetic_manager.utils;

import com.cosmetic_manager.cosmetic_manager.dto.RoutineProductDto;
import com.cosmetic_manager.cosmetic_manager.exceptions.ProductNotFoundException;
import com.cosmetic_manager.cosmetic_manager.exceptions.RoutineNotFoundException;
import com.cosmetic_manager.cosmetic_manager.model.Product;
import com.cosmetic_manager.cosmetic_manager.model.Routine;
import com.cosmetic_manager.cosmetic_manager.model.RoutineProduct;
import com.cosmetic_manager.cosmetic_manager.model.UserProductUsage;
import com.cosmetic_manager.cosmetic_manager.service.RoutineService;
import com.cosmetic_manager.cosmetic_manager.service.UserProductUsageService;
import org.springframework.stereotype.Component;

@Component
public class RoutineProductUtil {
    private final RoutineService routineService;
    private final UserProductUsageService userProductUsageService;

    public RoutineProductUtil(RoutineService routineService, UserProductUsageService userProductUsageService) {
        this.routineService = routineService;
        this.userProductUsageService = userProductUsageService;
    }

    public RoutineProduct fromRoutineProductDtoToRoutineProduct(RoutineProductDto routineProductDto) {
        Routine routine = routineService.getRoutineById(routineProductDto.getRoutineId());
        int user_id = routine.getUser().getId();
        UserProductUsage userProductUsage = userProductUsageService.getUserProductUsage(user_id, routineProductDto.getProductId());
        Product product = userProductUsage.getId().getProduct();

        return RoutineProduct.builder()
                .id(new RoutineProductPK(routine, product))
                .stepOrder(routineProductDto.getStepOrder())
                .note(routineProductDto.getNote())
                .build();
    }
}
