package com.cosmetic_manager.cosmetic_manager.model;

import com.cosmetic_manager.cosmetic_manager.utils.SkinType;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private SkinType skinType;

    private Date birthDate;
    private Date registrationDate;

    @PrePersist
    protected void onCreate() {
        registrationDate = new Date();
    }
}
