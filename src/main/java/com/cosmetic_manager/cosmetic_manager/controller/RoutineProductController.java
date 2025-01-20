package com.cosmetic_manager.cosmetic_manager.controller;

import com.cosmetic_manager.cosmetic_manager.dto.RoutineProductDto;
import com.cosmetic_manager.cosmetic_manager.model.RoutineProduct;
import com.cosmetic_manager.cosmetic_manager.service.RoutineProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/routine_products")
@Validated
@Tag(name = "Routine Product API", description = "Operations related to products in routines")
public class RoutineProductController {
    private final RoutineProductService routineProductService;

    public RoutineProductController(RoutineProductService routineProductService) {
        this.routineProductService = routineProductService;
    }

    @Operation(summary = "Get all products in a routine",
            description = "Get all products in a routine")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Products found", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = RoutineProduct.class))) }),
            @ApiResponse(responseCode = "404", description = "Routine not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/get_all")
    public ResponseEntity<List<RoutineProduct>> getAllRoutineProducts(@Parameter(description = "The ID of the routine whose products are fetched") @RequestParam int routine_id) {
        return ResponseEntity.ok(routineProductService.getRoutineProductsByRoutineId(routine_id));
    }

    @Operation(summary = "Add a product to a routine",
            description = "Add a product from user's collection to a routine")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product added successfully", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = RoutineProduct.class)) }),
            @ApiResponse(responseCode = "404", description = "Routine or product not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping("/add_product")
    public ResponseEntity<RoutineProduct> addProductToRoutine(@RequestBody RoutineProductDto routineProductDto) {
        return ResponseEntity.ok(routineProductService.addNewRoutineProduct(routineProductDto));
    }

    @Operation(summary = "Edit a product in a routine",
            description = "Edit the stepOrder or notes of a product in a routine")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product edited successfully", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = RoutineProduct.class)) }),
            @ApiResponse(responseCode = "404", description = "Routine not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PatchMapping("/edit_product")
    public ResponseEntity<RoutineProduct> editRoutineProduct(@RequestBody RoutineProductDto routineProductDto) {
        return ResponseEntity.ok(routineProductService.updateRoutineProduct(routineProductDto));
    }

    @Operation(summary = "Delete a product from a routine",
            description = "Delete a product from a routine")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product deleted successfully", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = RoutineProduct.class)) }),
            @ApiResponse(responseCode = "404", description = "Routine not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @DeleteMapping("/delete_product")
    public ResponseEntity<RoutineProduct> deleteRoutineProduct(@Parameter(description = "The ID of the routine") @RequestParam int routine_id, @Parameter(description = "The ID of the product") @RequestParam int product_id) {
        return ResponseEntity.ok(routineProductService.deleteRoutineProduct(routine_id, product_id));
    }
}
