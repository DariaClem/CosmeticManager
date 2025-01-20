package com.cosmetic_manager.cosmetic_manager.controller;
import com.cosmetic_manager.cosmetic_manager.dto.RoutineCreateDto;
import com.cosmetic_manager.cosmetic_manager.dto.RoutineDto;
import com.cosmetic_manager.cosmetic_manager.dto.RoutineUpdateDto;
import com.cosmetic_manager.cosmetic_manager.model.Routine;
import com.cosmetic_manager.cosmetic_manager.model.User;
import com.cosmetic_manager.cosmetic_manager.service.RoutineService;
import com.cosmetic_manager.cosmetic_manager.utils.Frequency;
import com.cosmetic_manager.cosmetic_manager.utils.SkinType;
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

@WebMvcTest(controllers = RoutineController.class)
public class RoutineControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private RoutineService routineService;

    private User user;
    private Routine routine;
    private RoutineCreateDto routineCreateDto;
    private RoutineUpdateDto routineUpdateDto;
    private List<Routine> routines;

    @BeforeEach
    public void setUp() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = dateFormat.parse("1999-12-12");

        user = new User(1, "stefana", "stefana@gmail.com", "Stefana123!", SkinType.OILY, birthDate, new Date());

        routine = new Routine(1, user, "Morning routine", "Cleanse, tone, moisturize", 0, Frequency.DAILY, new Date());

        routineCreateDto = new RoutineCreateDto("Morning routine", "Cleanse, tone, moisturize", 0, Frequency.DAILY, user.getId());
        routineUpdateDto = new RoutineUpdateDto(1, "Evening routine", "Cleanse, tone, moisturize", 1, Frequency.OCCASIONALLY);

        routines = List.of(routine);
    }

    @Test
    public void getAllRoutines() throws Exception {
        when(routineService.getAllRoutines(user.getId())).thenReturn(routines);

        mockMvc.perform(get("/routines/get_all?user_id=" + user.getId())
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value(routine.getName()))
                .andExpect(jsonPath("$[0].description").value(routine.getDescription()));
    }

    @Test
    public void createRoutine() throws Exception {
        when(routineService.createNewRoutine(any())).thenReturn(routine);

        mockMvc.perform(post("/routines/create")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(routineCreateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(routineCreateDto.getName()))
                .andExpect(jsonPath("$.description").value(routineCreateDto.getDescription()));
    }

    @Test
    public void updateRoutine() throws Exception {
        Routine updatedRoutine = routine;
        updatedRoutine.setName(routineUpdateDto.getName());
        updatedRoutine.setFrequency(routineUpdateDto.getFrequency());

        when(routineService.updateRoutine(any())).thenReturn(updatedRoutine);

        mockMvc.perform(patch("/routines/update?routine_id=" + routineUpdateDto.getId())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(routineUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(routineUpdateDto.getName()))
                .andExpect(jsonPath("$.frequency").value(routineUpdateDto.getFrequency().toString()));
    }

    @Test
    public void deleteRoutine() throws Exception {
        when(routineService.deleteRoutine(1)).thenReturn(routine);

        mockMvc.perform(delete("/routines/delete?routine_id=1")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }
}
