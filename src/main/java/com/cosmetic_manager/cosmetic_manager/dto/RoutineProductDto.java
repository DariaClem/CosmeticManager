package com.cosmetic_manager.cosmetic_manager.dto;

import com.cosmetic_manager.cosmetic_manager.utils.RoutineProductPK;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoutineProductDto {
    private int routineId;
    private int productId;

    @Min(value=1, message = "Step order must be at least 0")
    private int stepOrder;

    private String note;
}
