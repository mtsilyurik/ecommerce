package ru.miaat.Ecommerce.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name="addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Street is required")
    private String street;

    @NotNull(message = "City is required")
    private String city;

    @NotNull(message = "State is required")
    private String state;

    @NotNull(message = "Zip is required")
    private String zip;

    @NotNull(message = "Country is required")
    private String country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @Column(name="created_ad")
    private final LocalDateTime createdAt = LocalDateTime.now();
}
