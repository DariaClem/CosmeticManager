package com.cosmetic_manager.cosmetic_manager.controller;

import com.cosmetic_manager.cosmetic_manager.dto.RoutineCreateDto;
import com.cosmetic_manager.cosmetic_manager.dto.RoutineUpdateDto;
import com.cosmetic_manager.cosmetic_manager.model.Routine;
import com.cosmetic_manager.cosmetic_manager.service.RoutineService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/routines")
@Validated
public class RoutineController {
    private final RoutineService routineService;

    public RoutineController(RoutineService routineService) {
        this.routineService = routineService;
    }

    @GetMapping("/get_all")
    public ResponseEntity<List<Routine>> getAllRoutines(int user_id) {
        return ResponseEntity.ok(routineService.getAllRoutines(user_id));
    }

    @PostMapping("/create")
    public ResponseEntity<Routine> createNewRoutine(RoutineCreateDto routineCreateDto) {
        return ResponseEntity.ok(routineService.createNewRoutine(routineCreateDto));
    }

    @PatchMapping("/update")
    public ResponseEntity<Routine> updateRoutine(RoutineUpdateDto routineUpdateDto) {
        return ResponseEntity.ok(routineService.updateRoutine(routineUpdateDto));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Routine> deleteRoutine(int routine_id) {
        return ResponseEntity.ok(routineService.deleteRoutine(routine_id));
    }
}
