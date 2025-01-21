package com.cosmetic_manager.cosmetic_manager.controller;

import com.cosmetic_manager.cosmetic_manager.dto.LoginDto;
import com.cosmetic_manager.cosmetic_manager.dto.UserDto;
import com.cosmetic_manager.cosmetic_manager.model.User;
import com.cosmetic_manager.cosmetic_manager.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Validated
@Tag(name = "User API", description = "Operations related to user management")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Create account",
            description = "User provides username, email, password, skinType and birthDate to create an account.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User created successfully", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "409", description = "User already exists", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping("/create_account")
    public ResponseEntity<User> createAccount(@RequestBody @Valid UserDto userDto) {
        return ResponseEntity.ok(userService.createNewUser(userDto));
    }

    @Operation(summary = "Login",
            description = "User provides email and password to login")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User logged in successfully", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody @Valid LoginDto loginDto) {
        return ResponseEntity.ok(userService.login(loginDto));
    }

    @Operation(summary = "Get user by email",
            description = "Get user by email")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User found", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content= @Content)
    })
    @GetMapping("/get_by_email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok().body(userService.getUserByEmail(email));
    }
}
