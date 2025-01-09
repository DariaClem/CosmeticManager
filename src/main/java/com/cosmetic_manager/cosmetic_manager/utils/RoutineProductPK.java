package com.cosmetic_manager.cosmetic_manager.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor
@Getter
public class RoutineProductPK {
    private int routineId;
    private int productId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoutineProductPK routineProductPK)) return false;

        return routineId == routineProductPK.routineId &&
               productId == routineProductPK.productId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(routineId, productId);
    }
}
