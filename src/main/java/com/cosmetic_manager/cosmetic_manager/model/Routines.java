package com.cosmetic_manager.cosmetic_manager.model;

import com.cosmetic_manager.cosmetic_manager.utils.Frequency;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.util.Date;

@Entity
@Table(name = "routines")
public class Routines {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Size(min=2, max=50, message = "Name must be between 2 and 50 characters")
    private String name;

    private String description;

    private int usageCount = 0;
    private Frequency frequency;
    private Date createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }
}
