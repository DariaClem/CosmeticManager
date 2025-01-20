package com.cosmetic_manager.cosmetic_manager.controller;

import com.cosmetic_manager.cosmetic_manager.dto.RoutineProductDto;
import com.cosmetic_manager.cosmetic_manager.dto.UserProductUsageDto;
import com.cosmetic_manager.cosmetic_manager.model.*;
import com.cosmetic_manager.cosmetic_manager.service.RoutineProductService;
import com.cosmetic_manager.cosmetic_manager.service.UserProductUsageService;
import com.cosmetic_manager.cosmetic_manager.utils.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RoutineProductController.class)
public class RoutineProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private RoutineProductService routineProductService;

    private User user;
    private RoutineProductDto routineProductDto;
    private RoutineProduct routineProduct;
    private List<RoutineProduct> routineProducts;

    @BeforeEach
    public void setUp() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = dateFormat.parse("1999-12-12");

        user = new User(1, "stefana", "stefana@gmail.com", "Stefana123!", SkinType.OILY, birthDate, new Date());

        Category category = new Category(1, CategoryName.SKINCARE);
        Product product = new Product(1, "Cleanser", "Bioderma", category, "700ml");

        Routine routine = new Routine(1, user, "Morning routine", "Cleanse, tone, moisturize", 0, Frequency.DAILY,
                new Date());
        RoutineProductPK pk2 = new RoutineProductPK(routine, product);
        routineProduct = new RoutineProduct(pk2, 1, "Cleansing the face and neck");

        routineProductDto = new RoutineProductDto(routine.getId(), product.getId(), 1, "Cleansing the face");

        routineProducts = List.of(routineProduct);
    }

    @Test
    public void getAllRoutineProducts() throws Exception {
        when(routineProductService.getRoutineProductsByRoutineId(routineProduct.getId().getRoutine().getId())).thenReturn(routineProducts);

        mockMvc.perform(get("/routine_products/get_all?routine_id=" + routineProduct.getId().getRoutine().getId())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(user.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].stepOrder").value(routineProduct.getStepOrder()))
                .andExpect(jsonPath("$[0].note").value(routineProduct.getNote()));
    }

    @Test
    public void addProductToRoutine() throws Exception {
        when(routineProductService.addNewRoutineProduct(any())).thenReturn(routineProduct);

        mockMvc.perform(post("/routine_products/add_product")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(routineProductDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stepOrder").value(routineProduct.getStepOrder()))
                .andExpect(jsonPath("$.note").value(routineProduct.getNote()));
    }

    @Test
    public void updateRoutineProduct() throws Exception {
        RoutineProduct updatedRoutineProduct = routineProduct;
        updatedRoutineProduct.setStepOrder(2);
        updatedRoutineProduct.setNote("Cleansing the hands");

        when(routineProductService.updateRoutineProduct(any())).thenReturn(updatedRoutineProduct);

        mockMvc.perform(patch("/routine_products/edit_product")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(routineProductDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stepOrder").value(updatedRoutineProduct.getStepOrder()))
                .andExpect(jsonPath("$.note").value(updatedRoutineProduct.getNote()));
    }

    @Test
    public void deleteRoutineProduct() throws Exception {
        when(routineProductService.deleteRoutineProduct(routineProduct.getId().getRoutine().getId(), routineProduct.getId().getProduct().getId()))
                .thenReturn(routineProduct);

        mockMvc.perform(delete("/routine_products/delete_product?routine_id=" + routineProduct.getId().getRoutine().getId() + "&product_id=" + routineProduct.getId().getProduct().getId())
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }
}
