package com.cosmetic_manager.cosmetic_manager.utils;

import com.cosmetic_manager.cosmetic_manager.model.Product;
import com.cosmetic_manager.cosmetic_manager.model.Routine;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Embeddable
public class RoutineProductPK implements Serializable {
    @ManyToOne
    @JoinColumn(name = "routine_id", referencedColumnName = "id", nullable = false)
    private Routine routine;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private Product product;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoutineProductPK routineProductPK)) return false;

        return Objects.equals(routine.getId(), routineProductPK.routine.getId()) &&
                Objects.equals(product.getId(), routineProductPK.product.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(routine.getId(), product.getId());
    }
}
