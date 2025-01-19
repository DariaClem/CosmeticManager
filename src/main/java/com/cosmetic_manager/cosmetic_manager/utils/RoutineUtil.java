package com.cosmetic_manager.cosmetic_manager.utils;

import com.cosmetic_manager.cosmetic_manager.dto.RoutineCreateDto;
import com.cosmetic_manager.cosmetic_manager.dto.RoutineDto;
import com.cosmetic_manager.cosmetic_manager.exceptions.UserNotFoundException;
import com.cosmetic_manager.cosmetic_manager.model.Routine;
import com.cosmetic_manager.cosmetic_manager.model.User;
import com.cosmetic_manager.cosmetic_manager.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class RoutineUtil {
    private final UserRepository userRepository;

    public RoutineUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Routine fromRoutineDtoToRoutine(RoutineCreateDto routineDto) {
        User user = userRepository
                .findById(routineDto.getUser_id())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return Routine.builder()
                .user(user)
                .name(routineDto.getName())
                .description(routineDto.getDescription())
                .usageCount(routineDto.getUsageCount())
                .frequency(routineDto.getFrequency())
                .build();
    }
}
