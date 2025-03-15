package com.example.product_order_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Product is required")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;

    @NotNull(message = "Order is required")
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private Integer quantity = 1;

    @NotNull(message = "Price is required")
    private BigDecimal price;
}