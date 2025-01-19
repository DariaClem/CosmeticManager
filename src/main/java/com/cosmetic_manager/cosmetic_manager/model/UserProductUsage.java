package com.cosmetic_manager.cosmetic_manager.model;

import com.cosmetic_manager.cosmetic_manager.utils.UserProductUsagePK;
import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "user_product_usage")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProductUsage {
    @EmbeddedId
    private UserProductUsagePK id;

    private int usageCount = 0;
    private int PAO = 0;

    private Date openingDate;
}
