package com.cosmetic_manager.cosmetic_manager.dto;

import com.cosmetic_manager.cosmetic_manager.utils.Frequency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoutineCreateDto extends RoutineDto {
    private int user_id;

    public RoutineCreateDto(String name, String description, int usageCount, Frequency frequency, int userId) {
        super(name, description, usageCount, frequency);
        this.user_id = userId;
    }
}
