package com.example.product_order_service.entity;

import java.math.BigDecimal;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;


@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Product name is required")
    private String name;

    private String description;

    @NotNull(message = "Product price is required")
    private BigDecimal price;

    private BigDecimal discountedPrice;

    @Column(nullable = false)
    private String unit;    // e.g., piece, kg, packet, etc.

    @Size(max = 100, message = "Category must be less than 100 characters")
    private String category;   // e.g., electronics, clothing, etc.

    private String imageUrl;
}
