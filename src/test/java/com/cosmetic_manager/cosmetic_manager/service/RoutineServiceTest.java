package com.cosmetic_manager.cosmetic_manager.service;

import com.cosmetic_manager.cosmetic_manager.dto.RoutineCreateDto;
import com.cosmetic_manager.cosmetic_manager.exceptions.RoutineNotFoundException;
import com.cosmetic_manager.cosmetic_manager.model.Routine;
import com.cosmetic_manager.cosmetic_manager.model.User;
import com.cosmetic_manager.cosmetic_manager.repository.RoutineRepository;
import com.cosmetic_manager.cosmetic_manager.repository.UserRepository;
import com.cosmetic_manager.cosmetic_manager.utils.Frequency;
import com.cosmetic_manager.cosmetic_manager.utils.RoutineUtil;
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
public class RoutineServiceTest {
    @Mock
    private RoutineRepository routineRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoutineUtil routineUtil;

    @InjectMocks
    private RoutineService routineService;

    @Test
    @DisplayName("Get all routines for a user")
    void getAllRoutines() {
        // Arrange
        int userId = 1;
        User user = new User();
        user.setId(userId);

        Routine routine1 = new Routine(1, user, "Morning Routine", "Description 1", 0, Frequency.DAILY, new Date());
        Routine routine2 = new Routine(2, user, "Evening Routine", "Description 2", 0, Frequency.DAILY, new Date());

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(routineRepository.findByUserId(userId)).thenReturn(List.of(routine1, routine2));

        // Act
        var result = routineService.getAllRoutines(userId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(routine1, result.get(0));
        assertEquals(routine2, result.get(1));

        verify(userRepository, times(1)).findById(userId);
        verify(routineRepository, times(1)).findByUserId(userId);
    }

    @Test
    @DisplayName("Get routine by ID")
    void getRoutineById() {
        // Arrange
        int routineId = 1;
        User user = new User();
        Routine routine = new Routine(routineId, user, "Morning Routine", "Description", 0, Frequency.DAILY, new Date());

        when(routineRepository.findById(routineId)).thenReturn(Optional.of(routine));

        // Act
        var result = routineService.getRoutineById(routineId);

        // Assert
        assertNotNull(result);
        assertEquals(routineId, result.getId());
        assertEquals("Morning Routine", result.getName());

        verify(routineRepository, times(1)).findById(routineId);
    }

    @Test
    @DisplayName("Get routine by ID - not found")
    void getRoutineByIdNotFound() {
        // Arrange
        int routineId = 1;

        when(routineRepository.findById(routineId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RoutineNotFoundException.class, () -> routineService.getRoutineById(routineId));

        verify(routineRepository, times(1)).findById(routineId);
    }
}