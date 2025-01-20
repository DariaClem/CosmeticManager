package com.cosmetic_manager.cosmetic_manager.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewUpdateDto extends ReviewDto {
    private int id;

    public ReviewUpdateDto(int id, int rating, String comment) {
        super(rating, comment);
        this.id = id;
    }
}
