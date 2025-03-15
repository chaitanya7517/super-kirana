package com.example.product_order_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Product is required")
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity = 1;

    @NotNull(message = "Cart is required")
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @NotNull(message = "Price is required")
    private BigDecimal price;
}
