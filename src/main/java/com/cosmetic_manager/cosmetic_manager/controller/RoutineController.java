package com.cosmetic_manager.cosmetic_manager.controller;

import com.cosmetic_manager.cosmetic_manager.dto.RoutineCreateDto;
import com.cosmetic_manager.cosmetic_manager.dto.RoutineUpdateDto;
import com.cosmetic_manager.cosmetic_manager.model.Routine;
import com.cosmetic_manager.cosmetic_manager.service.RoutineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/routines")
@Validated
@Tag(name = "Routine API", description = "Operations related to routines of users")
public class RoutineController {
    private final RoutineService routineService;

    public RoutineController(RoutineService routineService) {
        this.routineService = routineService;
    }

    @Operation(summary = "Get all routines",
            description = "Get all routines of a user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Routines found", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Routine.class))) }),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/get_all")
    public ResponseEntity<List<Routine>> getAllRoutines(@Parameter(description = "The ID of the user whose routines are to be fetched") @RequestParam int user_id) {
        return ResponseEntity.ok(routineService.getAllRoutines(user_id));
    }

    @Operation(summary = "Create a new routine",
            description = "Create a new routine for a user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Routine created successfully", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Routine.class)) }),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping("/create")
    public ResponseEntity<Routine> createNewRoutine(@RequestBody @Valid RoutineCreateDto routineCreateDto) {
        return ResponseEntity.ok(routineService.createNewRoutine(routineCreateDto));
    }

    @Operation(summary = "Update a routine",
            description = "Update the name, description, usage count or frequency of a routine")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Routine updated successfully", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Routine.class)) }),
            @ApiResponse(responseCode = "404", description = "Routine not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PatchMapping("/update")
    public ResponseEntity<Routine> updateRoutine(@RequestBody @Valid RoutineUpdateDto routineUpdateDto) {
        return ResponseEntity.ok(routineService.updateRoutine(routineUpdateDto));
    }

    @Operation(summary = "Delete a routine",
            description = "Delete a routine")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Routine deleted successfully", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Routine.class)) }),
            @ApiResponse(responseCode = "404", description = "Routine not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @DeleteMapping("/delete")
    public ResponseEntity<Routine> deleteRoutine(@Parameter(description = "The ID of the routine to be deleted") @RequestParam int routine_id) {
        return ResponseEntity.ok(routineService.deleteRoutine(routine_id));
    }
}
