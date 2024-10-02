package ru.miaat.Ecommerce.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    private String description;

    private String imageUrl;

    @NotBlank(message = "Price is required")
    private BigDecimal price;

    @Column(name="created_at")
    private final LocalDateTime createdAt = LocalDateTime.now();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
}
