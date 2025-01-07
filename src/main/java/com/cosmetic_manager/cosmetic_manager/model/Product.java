package com.cosmetic_manager.cosmetic_manager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(min=2, max=50, message = "Name must be between 2 and 50 characters")
    private String name;

    @Size(min=2, max=50, message = "Brand must be between 2 and 50 characters")
    private String brand;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    private Category category;

    private String quantity;
}
