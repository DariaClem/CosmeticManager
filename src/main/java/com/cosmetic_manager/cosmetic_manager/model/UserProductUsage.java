package com.cosmetic_manager.cosmetic_manager.model;

import com.cosmetic_manager.cosmetic_manager.utils.UserProductUsagePK;
import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.util.Date;

@Entity
@IdClass(UserProductUsagePK.class)
@Table(name = "user_product_usage")
@Data
public class UserProductUsage {
    @Id
    private int userId;
    @Id
    private int productId;

    private int usageCount = 0;
    private int PAO = 0;

    @Past(message = "Opening date must be in the past")
    private Date openingDate;
}
