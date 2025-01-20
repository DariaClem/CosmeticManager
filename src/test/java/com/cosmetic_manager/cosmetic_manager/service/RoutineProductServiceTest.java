package com.cosmetic_manager.cosmetic_manager.service;

import com.cosmetic_manager.cosmetic_manager.dto.RoutineProductDto;
import com.cosmetic_manager.cosmetic_manager.exceptions.ProductNotFoundException;
import com.cosmetic_manager.cosmetic_manager.exceptions.RoutineNotFoundException;
import com.cosmetic_manager.cosmetic_manager.model.*;
import com.cosmetic_manager.cosmetic_manager.repository.RoutineProductRepository;
import com.cosmetic_manager.cosmetic_manager.repository.RoutineRepository;
import com.cosmetic_manager.cosmetic_manager.repository.UserProductUsageRepository;
import com.cosmetic_manager.cosmetic_manager.utils.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoutineProductServiceTest {
    @Mock
    private RoutineProductRepository routineProductRepository;

    @Mock
    private RoutineProductUtil routineProductUtil;

    @Mock
    private RoutineService routineService;

    @Mock
    private UserProductUsageService userProductUsageService;

    @Mock
    private ProductService productService;

    @Mock
    private RoutineRepository routineRepository;

    @Mock
    private UserProductUsageRepository userProductUsageRepository;

    @InjectMocks
    private RoutineProductService routineProductService;

    private Routine routine;
    private User user;
    private Product product;
    private RoutineProduct routineProduct;
    private RoutineProductDto routineProductDto;
    private UserProductUsage userProductUsage;

    @BeforeEach
    void setUp() {
        user = new User(1, "stefana", "stefana@gmail.com", "Stefana123!", SkinType.OILY, new Date(), new Date());
        Object CategoryName;
        product = new Product(1, "Cleanser", "Bioderma", new Category(1, com.cosmetic_manager.cosmetic_manager.utils.CategoryName.SKINCARE), "700ml");
        routine = new Routine(1, user, "Morning routine", "Cleanse, tone, moisturize", 0, Frequency.DAILY, new Date());
        routineProductDto = new RoutineProductDto(routine.getId(), product.getId(), 1, "Cleansing the face");
        routineProduct = new RoutineProduct(new RoutineProductPK(routine, product), 1, "Cleansing the face and neck");
        userProductUsage = new UserProductUsage(new UserProductUsagePK(user, product), 5, 12, new Date());
    }

    @Test
    @DisplayName("Get routine product")
    void getRoutineProduct() {
        when(routineService.getRoutineById(routine.getId())).thenReturn(routine);
        when(userProductUsageService.getUserProductUsage(user.getId(), product.getId())).thenReturn(userProductUsage);
        when(productService.getProductById(product.getId())).thenReturn(product);
        when(routineProductRepository.findById(new RoutineProductPK(routine, product))).thenReturn(Optional.of(routineProduct));

        RoutineProduct result = routineProductService.getRoutineProduct(routine.getId(), product.getId());

        assertNotNull(result);
        assertEquals(routineProduct, result);

        verify(routineService, times(1)).getRoutineById(routine.getId());
        verify(userProductUsageService, times(1)).getUserProductUsage(user.getId(), product.getId());
        verify(productService, times(1)).getProductById(product.getId());
        verify(routineProductRepository, times(1)).findById(new RoutineProductPK(routine, product));
    }

    @Test
    @DisplayName("Delete routine product")
    void deleteRoutineProduct() {
        when(routineService.getRoutineById(routine.getId())).thenReturn(routine);
        when(userProductUsageService.getUserProductUsage(user.getId(), product.getId())).thenReturn(userProductUsage);
        when(productService.getProductById(product.getId())).thenReturn(product);
        when(routineProductRepository.findById(new RoutineProductPK(routine, product))).thenReturn(Optional.of(routineProduct));

        RoutineProduct result = routineProductService.deleteRoutineProduct(routine.getId(), product.getId());

        assertNotNull(result);
        assertEquals(routineProduct, result);

        verify(routineService, times(1)).getRoutineById(routine.getId());
        verify(userProductUsageService, times(1)).getUserProductUsage(user.getId(), product.getId());
        verify(productService, times(1)).getProductById(product.getId());
        verify(routineProductRepository, times(1)).findById(new RoutineProductPK(routine, product));
        verify(routineProductRepository, times(1)).delete(routineProduct);
    }

    @Test
    @DisplayName("Get routine products by routine ID")
    void getRoutineProductsByRoutineId() {
        when(routineRepository.findById(routine.getId())).thenReturn(Optional.of(routine));
        when(routineProductRepository.findById_RoutineId(routine.getId())).thenReturn(List.of(routineProduct));

        List<RoutineProduct> result = routineProductService.getRoutineProductsByRoutineId(routine.getId());

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(routineProduct, result.get(0));

        verify(routineRepository, times(1)).findById(routine.getId());
        verify(routineProductRepository, times(1)).findById_RoutineId(routine.getId());
    }
}