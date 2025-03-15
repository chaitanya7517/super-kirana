package com.example.product_order_service.repo;

import com.example.product_order_service.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepo extends JpaRepository<Cart, Long> {
    Cart findByUserName(String userName);
}
