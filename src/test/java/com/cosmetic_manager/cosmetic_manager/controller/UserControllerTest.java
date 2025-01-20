package com.cosmetic_manager.cosmetic_manager.controller;

import com.cosmetic_manager.cosmetic_manager.dto.LoginDto;
import com.cosmetic_manager.cosmetic_manager.dto.UserDto;
import com.cosmetic_manager.cosmetic_manager.exceptions.UserAlreadyExistsException;
import com.cosmetic_manager.cosmetic_manager.model.User;
import com.cosmetic_manager.cosmetic_manager.service.UserService;
import com.cosmetic_manager.cosmetic_manager.utils.SkinType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private UserService userService;

    @Test
    public void createUser() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = dateFormat.parse("1999-12-12");

        UserDto userDto = new UserDto("stefana", "stefana@gmail.com", "Stefana123!", SkinType.OILY, birthDate);

        when(userService.createNewUser(any())).thenReturn(new User(0, "stefana", "stefana@gmail.com", "Stefana123!",
                SkinType.OILY, birthDate, new Date()));

        mockMvc.perform(post("/users/create_account")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(userDto.getUsername()))
                .andExpect(jsonPath("$.email").value(userDto.getEmail()));
    }

    @Test
    public void createUserAlreadyExists() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = dateFormat.parse("1999-12-12");

        UserDto userDto = new UserDto("stefana", "stefana@gmail.com", "Stefana123!", SkinType.OILY, birthDate);

        when(userService.createNewUser(any())).thenThrow(new UserAlreadyExistsException("User with email " + userDto.getEmail() + " already exists"));

        mockMvc.perform(post("/users/create_account")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isConflict());
    }

    @Test
    public void login() throws Exception {
        LoginDto loginDto = new LoginDto("stefana@gmail.com", "Stefana123!");

        when(userService.login(any())).thenReturn(new User(0, "stefana", "stefana@gmail.com", "Stefana123!",
                SkinType.OILY, new Date(), new Date()));

        mockMvc.perform(post("/users/login")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("stefana"));
    }

    @Test
    public void getUserByEmail() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = dateFormat.parse("1999-12-12");

        when(userService.getUserByEmail(any())).thenReturn(new User(0, "stefana", "stefana@gmail.com", "Stefana123!",
                SkinType.OILY, birthDate, new Date()));

        mockMvc.perform(get("/users/get_by_email/{email}", "stefana@gmail.com")
                        .contentType("application/json"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.username").value("stefana"))
                        .andExpect(jsonPath("$.id").value(0));
    }
}
