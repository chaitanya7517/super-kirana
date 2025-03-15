package com.example.product_order_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Username is required")
    private String userName;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items;

    @NotNull(message = "Total amount is required")
    private BigDecimal totalAmount;

    //order status: 0 - pending, 1 - confirmed, 2 - shipped, 3 - delivered
    private int status = 0;

    private LocalDateTime orderDate = LocalDateTime.now();
}