package com.cosmetic_manager.cosmetic_manager.controller;

import com.cosmetic_manager.cosmetic_manager.model.Category;
import com.cosmetic_manager.cosmetic_manager.model.Product;
import com.cosmetic_manager.cosmetic_manager.service.ProductService;
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

@WebMvcTest(controllers = ProductController.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProductService productService;

    @Test
    public void getAllProducts() throws Exception {
        Category category = new Category(1, CategoryName.MAKEUP);
        Product product1 = new Product(1, "Eyeshadow palette", "Huda beauty", category, "100gr");
        Product product2 = new Product(2, "Setting spray", "Milk Makeup", category, "50ml");

        List<Product> products = List.of(product1, product2);

        when(productService.getAllProducts()).thenReturn(products);

        mockMvc.perform(get("/products/get_all")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value(product1.getName()))
                .andExpect(jsonPath("$[1].name").value(product2.getName()))
                .andExpect(jsonPath("$[0].brand").value(product1.getBrand()))
                .andExpect(jsonPath("$[1].quantity").value(product2.getQuantity()));
    }

    @Test
    public void createProduct() throws Exception {
        Category category = new Category(1, CategoryName.MAKEUP);
        Product product = new Product(1, "Eyeshadow palette", "Huda beauty", category, "100gr");

        when(productService.createNewProduct(any())).thenReturn(product);

        mockMvc.perform(post("/products/create_product")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(product.getName()))
                .andExpect(jsonPath("$.brand").value(product.getBrand()))
                .andExpect(jsonPath("$.quantity").value(product.getQuantity()));
    }

    @Test
    public void createProductWithWrongQuantity() throws Exception {
        Category category = new Category(1, CategoryName.MAKEUP);
        Product product = new Product(1, "Eyeshadow palette", "Huda beauty", category, "100");

        when(productService.createNewProduct(any())).thenThrow(new IllegalArgumentException("Quantity must be of type 'number+unit(gr/ml/unit)'"));

        mockMvc.perform(post("/products/create_product")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getProductByCategory() throws Exception {
        Category category = new Category(1, CategoryName.MAKEUP);
        Product product1 = new Product(1, "Eyeshadow palette", "Huda beauty", category, "100gr");
        Product product2 = new Product(2, "Setting spray", "Milk Makeup", category, "50ml");
        Product product3 = new Product(3, "Lipstick", "Fenty Beauty", category, "3gr");

        List<Product> products = List.of(product1, product2, product3);

        when(productService.getProductsByCategoryId(1)).thenReturn(products);

        mockMvc.perform(get("/products/get_by_category?category_id=1")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].name").value(product1.getName()))
                .andExpect(jsonPath("$[1].name").value(product2.getName()))
                .andExpect(jsonPath("$[1].brand").value(product2.getBrand()))
                .andExpect(jsonPath("$[2].quantity").value(product3.getQuantity()));
    }

    @Test
    public void getProductByBrand() throws Exception {
        Category category = new Category(1, CategoryName.MAKEUP);
        Product product1 = new Product(1, "Eyeshadow palette", "Huda beauty", category, "100gr");
        Product product2 = new Product(2, "Setting spray", "Milk Makeup", category, "50ml");
        Product product3 = new Product(3, "Lipstick", "Fenty Beauty", category, "3gr");

        List<Product> products = List.of(product1, product2, product3);

        when(productService.getProductsByBrand("Huda beauty")).thenReturn(List.of(product1));

        mockMvc.perform(get("/products/get_by_brand?brand=Huda beauty")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value(product1.getName()))
                .andExpect(jsonPath("$[0].brand").value(product1.getBrand()))
                .andExpect(jsonPath("$[0].quantity").value(product1.getQuantity()));
    }

    @Test
    public void getProductByName() throws Exception {
        Category category = new Category(1, CategoryName.MAKEUP);
        Product product1 = new Product(1, "Eyeshadow palette", "Huda beauty", category, "100gr");
        Product product2 = new Product(2, "Setting spray", "Milk Makeup", category, "50ml");
        Product product3 = new Product(3, "Lipstick", "Fenty Beauty", category, "3gr");

        List<Product> products = List.of(product1, product2, product3);

        when(productService.getProductsByName("Lipstick")).thenReturn(List.of(product3));

        mockMvc.perform(get("/products/get_by_name?name=Lipstick")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value(product3.getName()))
                .andExpect(jsonPath("$[0].brand").value(product3.getBrand()))
                .andExpect(jsonPath("$[0].quantity").value(product3.getQuantity()));
    }
}
