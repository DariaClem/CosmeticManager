package com.cosmetic_manager.cosmetic_manager.service;

import com.cosmetic_manager.cosmetic_manager.dto.RoutineCreateDto;
import com.cosmetic_manager.cosmetic_manager.dto.RoutineUpdateDto;
import com.cosmetic_manager.cosmetic_manager.exceptions.RoutineNotFoundException;
import com.cosmetic_manager.cosmetic_manager.exceptions.UserNotFoundException;
import com.cosmetic_manager.cosmetic_manager.model.Routine;
import com.cosmetic_manager.cosmetic_manager.repository.RoutineProductRepository;
import com.cosmetic_manager.cosmetic_manager.repository.RoutineRepository;
import com.cosmetic_manager.cosmetic_manager.repository.UserRepository;
import com.cosmetic_manager.cosmetic_manager.utils.RoutineUtil;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoutineService {
    private final RoutineRepository routineRepository;
    private final RoutineUtil routineUtil;
    private final UserRepository userRepository;
    private final RoutineProductRepository routineProductRepository;

    public RoutineService(RoutineRepository routineRepository, RoutineUtil routineUtil, UserRepository userRepository, RoutineProductRepository routineProductRepository) {
        this.routineRepository = routineRepository;
        this.routineUtil = routineUtil;
        this.userRepository = userRepository;
        this.routineProductRepository = routineProductRepository;
    }

    public List<Routine> getAllRoutines(int user_id) {
        if (userRepository.findById(user_id).isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        return routineRepository.findByUserId(user_id);
    }

    public Routine getRoutineById(int id) {
        return routineRepository.findById(id).orElseThrow(() -> new RoutineNotFoundException("Routine not found"));
    }

    public Routine createNewRoutine(RoutineCreateDto routineDto) {
        return routineRepository.save(routineUtil.fromRoutineDtoToRoutine(routineDto));
    }

    public Routine updateRoutine(RoutineUpdateDto routineDto) {
        Routine routine = routineRepository.findById(routineDto.getId()).orElseThrow(() -> new RoutineNotFoundException("Routine not found"));

        routine.setName(routineDto.getName());
        routine.setDescription(routineDto.getDescription());
        routine.setUsageCount(routineDto.getUsageCount());
        routine.setFrequency(routineDto.getFrequency());

        return routineRepository.save(routine);
    }

    @Transactional
    public Routine deleteRoutine(int id) {
        Routine routine = routineRepository.findById(id).orElseThrow(() -> new RoutineNotFoundException("Routine not found"));

        routineProductRepository.deleteRoutineProductsById_RoutineId(id);

        routineRepository.delete(routine);

        return routine;
    }
}
