package com.cosmetic_manager.cosmetic_manager.dto;

import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProductUsageDto {
    private int userId;
    private int productId;

    private int usageCount = 0;
    private int PAO = 0;

    @Past(message = "Opening date must be in the past")
    private Date openingDate;
}
