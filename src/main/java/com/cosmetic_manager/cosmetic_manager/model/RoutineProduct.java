package com.cosmetic_manager.cosmetic_manager.model;

import com.cosmetic_manager.cosmetic_manager.utils.RoutineProductPK;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "routine_product")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoutineProduct {
    @EmbeddedId
    private RoutineProductPK id;

    private int stepOrder;
    private String note;
}
