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

    public ReviewCreateDto(int productId, int userId, int rating, String comment) {
        super(rating, comment);
        this.product_id = productId;
        this.user_id = userId;
    }
}
