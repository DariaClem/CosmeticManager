package com.cosmetic_manager.cosmetic_manager.utils;

import java.io.Serializable;
import java.util.Objects;

import com.cosmetic_manager.cosmetic_manager.model.Product;
import com.cosmetic_manager.cosmetic_manager.model.User;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class UserProductUsagePK implements Serializable {
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private Product product;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserProductUsagePK userProductUsagePK)) return false;

        return Objects.equals(user.getId(), userProductUsagePK.user.getId()) &&
                Objects.equals(product.getId(), userProductUsagePK.product.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(user.getId(), product.getId());
    }
}
