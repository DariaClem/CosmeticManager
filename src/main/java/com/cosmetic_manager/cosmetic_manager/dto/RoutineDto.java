package com.cosmetic_manager.cosmetic_manager.dto;

import com.cosmetic_manager.cosmetic_manager.utils.Frequency;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RoutineDto {
    @Size(min=2, max=50, message = "Name must be between 2 and 50 characters")
    private String name;

    private String description;

    private int usageCount;
    private Frequency frequency;
}
