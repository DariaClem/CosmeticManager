package com.cosmetic_manager.cosmetic_manager.controller;

import com.cosmetic_manager.cosmetic_manager.dto.CategoryDto;
import com.cosmetic_manager.cosmetic_manager.model.Category;
import com.cosmetic_manager.cosmetic_manager.service.CategoryService;
import com.cosmetic_manager.cosmetic_manager.utils.CategoryName;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CategoryController.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private CategoryService categoryService;

    @Test
    public void getAllCategories() throws Exception {
        CategoryDto categoryDto1 = new CategoryDto(CategoryName.MAKEUP);
        CategoryDto categoryDto2 = new CategoryDto(CategoryName.SKINCARE);

        Category category1 = new Category(1, CategoryName.MAKEUP);
        Category category2 = new Category(2, CategoryName.SKINCARE);

        List<Category> categories = List.of(category1, category2);

        when(categoryService.getAllCategories()).thenReturn(categories);

        mockMvc.perform(get("/categories/get_all")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value(categoryDto1.getName().toString()))
                .andExpect(jsonPath("$[1].name").value(categoryDto2.getName().toString()));
    }

    @Test
    public void createCategory() throws Exception {
        CategoryDto categoryDto = new CategoryDto(CategoryName.MAKEUP);
        Category category = new Category(1, CategoryName.MAKEUP);

        when(categoryService.createNewCategory(any())).thenReturn(category);

        mockMvc.perform(post("/categories/create_category")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(categoryDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(categoryDto.getName().toString()));
        }
}
