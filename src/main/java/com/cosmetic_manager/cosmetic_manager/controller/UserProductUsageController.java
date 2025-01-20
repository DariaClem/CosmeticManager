package com.cosmetic_manager.cosmetic_manager.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cosmetic_manager.cosmetic_manager.dto.UserProductUsageDto;
import com.cosmetic_manager.cosmetic_manager.model.UserProductUsage;
import com.cosmetic_manager.cosmetic_manager.service.UserProductUsageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/user_product_usages")
@Validated
@Tag(name = "User Product Usage API", description = "Operations related to products added by users in their collection")
public class UserProductUsageController {
    private final UserProductUsageService userProductUsageService;

    public UserProductUsageController(UserProductUsageService userProductUsageService) {
        this.userProductUsageService = userProductUsageService;
    }

    @Operation(summary = "Get all user product usages",
            description = "Get all products added by a user in their collection")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Products found", content = { @Content(mediaType =
                    "application/json", array = @ArraySchema(schema =
            @Schema(implementation = UserProductUsage.class))) }),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/get_all")
    public ResponseEntity<List<UserProductUsage>> getAllUserProductUsage(@Parameter(description = "The ID of the user whose product usage is to be fetched") @RequestParam int user_id) {
        return ResponseEntity.ok(userProductUsageService.getAllUserProductUsages(user_id));
    }

    @Operation(summary = "Get an user product usage",
        description = "Get an user product usage information by its id")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User product usage found", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UserProductUsage.class)) }),
        @ApiResponse(responseCode = "404", description = "User product usage not found", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/get_by_product")
    public ResponseEntity<UserProductUsage> getUserProductUsageByProductId(@Parameter(description = "The ID of the user") @RequestParam int user_id, @Parameter(description = "The ID of the product") @RequestParam int product_id) {
        return ResponseEntity.ok(userProductUsageService.getUserProductUsage(user_id, product_id));
    }

    @Operation(summary = "Add a product usage",
            description = "Add a product usage to the user's collection")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product usage added successfully", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UserProductUsage.class)) }),
            @ApiResponse(responseCode = "404", description = "User or product not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping("/add_product_usage")
    public ResponseEntity<UserProductUsage> addUserProductUsage(@RequestBody UserProductUsageDto userProductUsageDto) {
        return ResponseEntity.ok(userProductUsageService.addUserProductUsage(userProductUsageDto));
    }

    @Operation(summary = "Increase product usage",
            description = "Increase the usage count of a product in the user's collection")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product usage increased successfully", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UserProductUsage.class)) }),
            @ApiResponse(responseCode = "404", description = "User or product not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PatchMapping("/increase_usage")
    public ResponseEntity<UserProductUsage> increaseProductUsage(@Parameter(description = "The ID of the user") @RequestParam int user_id, @Parameter(description = "The ID of the product to increase usage count") @RequestParam int product_id) {
        return ResponseEntity.ok(userProductUsageService.increaseProductUsage(user_id, product_id));
    }

    @Operation(summary = "Edit product usage",
            description = "Edit the usage count, PAO and opening date of a product in the user's collection")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product usage edited successfully", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UserProductUsage.class)) }),
            @ApiResponse(responseCode = "404", description = "User or product not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PatchMapping("/edit_product_usage")
    public ResponseEntity<UserProductUsage> editUserProductUsage(@RequestBody UserProductUsageDto userProductUsageDto) {
        return ResponseEntity.ok(userProductUsageService.editUserProductUsage(userProductUsageDto));
    }

    @Operation(summary = "Delete product usage",
            description = "Delete a product from the user's collection")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product usage deleted successfully", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UserProductUsage.class)) }),
            @ApiResponse(responseCode = "404", description = "User or product not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @DeleteMapping("/delete_product_usage")
    public ResponseEntity<UserProductUsage> deleteUserProductUsage(@Parameter(description = "The ID of the user") @RequestParam int user_id, @Parameter(description = "The ID of the product to be deleted") @RequestParam int product_id) {
        return ResponseEntity.ok(userProductUsageService.deleteUserProductUsage(user_id, product_id));
    }

    @Operation(summary = "Get expired products",
            description = "Get all expired products in the user's collection")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Expired products found", content =
                    { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation =
                            UserProductUsage.class))) }),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/get_expired")
    public ResponseEntity<List<UserProductUsage>> getExpiredProducts(@Parameter(description = "The ID of the user whose expired products are to be fetched") @RequestParam int user_id) {
        return ResponseEntity.ok(userProductUsageService.getExpiredProducts(user_id));
    }

    @Operation(summary = "Get products that will expire soon",
            description = "Get all products that will expire in the given period in days")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Expiring products found", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation =
                    UserProductUsage.class))) }),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/get_expiring_soon")
    public ResponseEntity<List<UserProductUsage>> getProductsThatWillExpire(@Parameter(description = "The ID of the user whose expiring products are to be fetched") @RequestParam int user_id, @Parameter(description = "The period in days in which the products will expire") @RequestParam int days) {
        return ResponseEntity.ok(userProductUsageService.getProductsThatWillExpire(user_id, days));
    }

}
