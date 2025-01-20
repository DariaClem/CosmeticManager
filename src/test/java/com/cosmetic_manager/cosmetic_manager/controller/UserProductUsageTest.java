package com.cosmetic_manager.cosmetic_manager.controller;

import com.cosmetic_manager.cosmetic_manager.dto.UserProductUsageDto;
import com.cosmetic_manager.cosmetic_manager.model.Category;
import com.cosmetic_manager.cosmetic_manager.model.Product;
import com.cosmetic_manager.cosmetic_manager.model.User;
import com.cosmetic_manager.cosmetic_manager.model.UserProductUsage;
import com.cosmetic_manager.cosmetic_manager.service.UserProductUsageService;
import com.cosmetic_manager.cosmetic_manager.utils.CategoryName;
import com.cosmetic_manager.cosmetic_manager.utils.SkinType;
import com.cosmetic_manager.cosmetic_manager.utils.UserProductUsagePK;
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

@WebMvcTest(controllers = UserProductUsageController.class)
public class UserProductUsageTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private UserProductUsageService userProductUsageService;

    private User user1;
    private Category category;
    private Product product1;
    private Product product2;
    private UserProductUsageDto userProductUsageDto1;
    private UserProductUsageDto userProductUsageDto2;
    private List<UserProductUsage> userProductUsages;

    @BeforeEach
    public void setUp() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = dateFormat.parse("1999-12-12");

        user1 = new User(1, "stefana", "stefana@gmail.com", "Stefana123!", SkinType.OILY, birthDate, new Date());

        category = new Category(1, CategoryName.MAKEUP);

        product1 = new Product(1, "Eyeshadow palette", "Huda beauty", category, "100gr");
        product2 = new Product(2, "Setting spray", "Milk Makeup", category, "50ml");

        Date openDate = dateFormat.parse("2024-12-12");

        userProductUsageDto1 = new UserProductUsageDto(user1.getId(), product1.getId(), 2, 1, openDate);
        userProductUsageDto2 = new UserProductUsageDto(user1.getId(), product2.getId(), 1, 6, openDate);

        UserProductUsagePK pk1 = new UserProductUsagePK(user1, product1);
        UserProductUsagePK pk2 = new UserProductUsagePK(user1, product2);

        UserProductUsage userProductUsage1 = new UserProductUsage(pk1, 2, 1, openDate);
        UserProductUsage userProductUsage2 = new UserProductUsage(pk2, 1, 6, openDate);

        userProductUsages = List.of(userProductUsage1, userProductUsage2);
    }

    @Test
    public void getAllUserProductUsage() throws Exception {
        when(userProductUsageService.getAllUserProductUsages(user1.getId())).thenReturn(userProductUsages);

        mockMvc.perform(get("/user_product_usages/get_all?user_id=" + user1.getId())
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[1].usageCount").value(userProductUsageDto2.getUsageCount()))
                .andExpect(jsonPath("$[0].pao").value(userProductUsageDto1.getPAO()));
    }

    @Test
    public void getUserProductUsage() throws Exception {
        when(userProductUsageService.getUserProductUsage(user1.getId(), product1.getId())).thenReturn(userProductUsages.get(0));

        mockMvc.perform(get("/user_product_usages/get_by_product?user_id=" + user1.getId() + "&product_id=" + product1.getId())
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.usageCount").value(userProductUsageDto1.getUsageCount()))
                .andExpect(jsonPath("$.pao").value(userProductUsageDto1.getPAO()));
    }

    @Test
    public void addUserProductUsage() throws Exception {
        when(userProductUsageService.addUserProductUsage(any())).thenReturn(userProductUsages.get(1));

        mockMvc.perform(post("/user_product_usages/add_product_usage")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userProductUsageDto2)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.usageCount").value(userProductUsageDto2.getUsageCount()))
                .andExpect(jsonPath("$.pao").value(userProductUsageDto2.getPAO()));
    }

    @Test
    public void increaseProductUsage() throws Exception {
        UserProductUsage userProductUsage = userProductUsages.get(0);
        userProductUsage.setUsageCount(userProductUsage.getUsageCount() + 1);

        when(userProductUsageService.increaseProductUsage(user1.getId(), product1.getId())).thenReturn(userProductUsage);

        mockMvc.perform(patch("/user_product_usages/increase_usage?user_id=" + user1.getId() + "&product_id=" + product1.getId())
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.usageCount").value(userProductUsageDto1.getUsageCount() + 1))
                .andExpect(jsonPath("$.pao").value(userProductUsageDto1.getPAO()));
    }

    @Test
    public void editProductUsage() throws Exception {
        UserProductUsage userProductUsage = userProductUsages.get(0);
        userProductUsage.setUsageCount(5);
        userProductUsage.setPAO(6);
        userProductUsage.setOpeningDate(new Date());

        UserProductUsageDto userProductUsageDto = new UserProductUsageDto(user1.getId(), product1.getId(), 5, 6, new Date());

        when(userProductUsageService.editUserProductUsage(any())).thenReturn(userProductUsage);

        mockMvc.perform(patch("/user_product_usages/edit_product_usage?user_id=" + user1.getId() + "&product_id=" + product1.getId() + "&usage_count=5&PAO=6&open_date=" + new Date())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userProductUsageDto1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.usageCount").value(userProductUsageDto.getUsageCount()))
                .andExpect(jsonPath("$.pao").value(userProductUsageDto.getPAO()));
    }

    @Test
    public void deleteProductUsage() throws Exception {
        when(userProductUsageService.deleteUserProductUsage(user1.getId(), product1.getId())).thenReturn(userProductUsages.get(0));

        mockMvc.perform(delete("/user_product_usages/delete_product_usage?user_id=" + user1.getId() + "&product_id=" + product1.getId())
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void getExpiredProducts() throws Exception {
        when(userProductUsageService.getExpiredProducts(user1.getId())).thenReturn(List.of(userProductUsages.get(0)));

        mockMvc.perform(get("/user_product_usages/get_expired?user_id=" + user1.getId())
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].usageCount").value(userProductUsageDto1.getUsageCount()))
                .andExpect(jsonPath("$[0].pao").value(userProductUsageDto1.getPAO()));
    }

    @Test
    public void getProductsThatWillExpire() throws Exception {
        when(userProductUsageService.getProductsThatWillExpire(user1.getId(), 365)).thenReturn(List.of(userProductUsages.get(1)));

        mockMvc.perform(get("/user_product_usages/get_expiring_soon?user_id=" + user1.getId() + "&days=365")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].usageCount").value(userProductUsageDto2.getUsageCount()))
                .andExpect(jsonPath("$[0].pao").value(userProductUsageDto2.getPAO()));
    }

}
