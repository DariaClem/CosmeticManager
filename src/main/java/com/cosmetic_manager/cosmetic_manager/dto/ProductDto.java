package com.cosmetic_manager.cosmetic_manager.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    @Size(min=2, max=50, message = "Name must be between 2 and 50 characters")
    private String name;

    @Size(min=2, max=50, message = "Brand must be between 2 and 50 characters")
    private String brand;

    private int category_id;

    @Pattern(regexp = "^[0-9]+(ml|gr|unit)$", message = "Quantity must be a number followed by a unit (ml, gr, " +
            "unit)")
    private String quantity;
}
