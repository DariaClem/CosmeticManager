package com.cosmetic_manager.cosmetic_manager.dto;

import com.cosmetic_manager.cosmetic_manager.utils.SkinType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @NotNull(message="Username is required")
    @Size(min=3, max=30, message = "Username must be between 3 and 30 characters")
    private String username;

    @NotNull(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @NotNull(message = "Password is required")
    @Size(min=8, message = "Password must be at least 8 characters")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character")
    private String password;

    private SkinType skinType;

    @Past(message = "Birthdate must be in the past")
    private Date birthDate;
}
