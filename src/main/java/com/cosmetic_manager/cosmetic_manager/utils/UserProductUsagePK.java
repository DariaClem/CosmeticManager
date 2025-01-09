package com.cosmetic_manager.cosmetic_manager.utils;

import java.io.Serializable;
import java.util.Objects;

import lombok.NoArgsConstructor;
import lombok.Getter;

@NoArgsConstructor
@Getter
public class UserProductUsagePK implements Serializable {
    private int userId;
    private int productId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserProductUsagePK userProductUsagePK)) return false;

        return userId == userProductUsagePK.userId &&
               productId == userProductUsagePK.productId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, productId);
    }
}
