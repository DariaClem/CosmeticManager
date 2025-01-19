package com.cosmetic_manager.cosmetic_manager.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewCreateDto extends ReviewDto {
    private int product_id;
    private int user_id;
}
