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
public class RoutineUpdateDto extends RoutineDto {
    private int id;

    public RoutineUpdateDto(int id, String name, String description, int usageCount, Frequency frequency) {
        super(name, description, usageCount, frequency);
        this.id = id;
    }
}
