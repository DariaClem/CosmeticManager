package com.cosmetic_manager.cosmetic_manager.service;

import com.cosmetic_manager.cosmetic_manager.dto.RoutineCreateDto;
import com.cosmetic_manager.cosmetic_manager.dto.RoutineUpdateDto;
import com.cosmetic_manager.cosmetic_manager.model.Routine;
import com.cosmetic_manager.cosmetic_manager.repository.RoutineRepository;
import com.cosmetic_manager.cosmetic_manager.utils.RoutineUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoutineService {
    private final RoutineRepository routineRepository;
    private final RoutineUtil routineUtil;

    public RoutineService(RoutineRepository routineRepository, RoutineUtil routineUtil) {
        this.routineRepository = routineRepository;
        this.routineUtil = routineUtil;
    }

    public List<Routine> getAllRoutines(int user_id) {
        return routineRepository.findByUserId(user_id);
    }

    public Routine getRoutineById(int id) {
        return routineRepository.findById(id).orElseThrow();
    }

    public Routine createNewRoutine(RoutineCreateDto routineDto) {
        return routineRepository.save(routineUtil.fromRoutineDtoToRoutine(routineDto));
    }

    public Routine updateRoutine(RoutineUpdateDto routineDto) {
        Routine routine = routineRepository.findById(routineDto.getId()).orElseThrow();

        routine.setName(routineDto.getName());
        routine.setDescription(routineDto.getDescription());
        routine.setUsageCount(routineDto.getUsageCount());
        routine.setFrequency(routineDto.getFrequency());

        return routineRepository.save(routine);
    }

    public Routine deleteRoutine(int id) {
        Routine routine = routineRepository.findById(id).orElseThrow();

        routineRepository.delete(routine);

        return routine;
    }
}
