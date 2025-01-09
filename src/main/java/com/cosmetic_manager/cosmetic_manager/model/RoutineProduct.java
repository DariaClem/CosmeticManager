package com.cosmetic_manager.cosmetic_manager.model;

import com.cosmetic_manager.cosmetic_manager.utils.RoutineProductPK;
import jakarta.persistence.*;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Entity
@IdClass(RoutineProductPK.class)
@Table(name = "routine_product")
@Data
public class RoutineProduct {
    @Id
    private int routineId;
    @Id
    private int productId;

    @Min(value=1, message = "Step order must be at least 0")
    private int stepOrder;

    private String note;
}
