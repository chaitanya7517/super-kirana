package com.example.product_order_service.controller;

import com.example.product_order_service.entity.Cart;
import com.example.product_order_service.entity.Order;
import com.example.product_order_service.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping
    public ResponseEntity<Cart> addCart(@RequestBody Cart cart) {
        Cart createdCart = cartService.addCart(cart);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCart);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCartById(@PathVariable Long id) {
        Optional<Cart> cart = cartService.getCartById(id);
        return cart.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCart(@PathVariable Long id, @RequestBody Cart cart) {
        Cart updatedCart = cartService.updateCart(id, cart);
        if (updatedCart != null) {
            return ResponseEntity.ok(updatedCart);
        }
        return ResponseEntity.badRequest().body("Cart with ID " + id + " not found");    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCart(@PathVariable Long id) {
        boolean isDeleted = cartService.deleteCart(id);
        if (isDeleted) {
            return ResponseEntity.ok().body("Cart with ID " + id + " was successfully deleted");
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/checkout/{cartId}")
    public ResponseEntity<Order> checkoutCart(@PathVariable Long cartId) {
        Order order = cartService.checkoutCart(cartId);
        if (order != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(order);
        }
        return ResponseEntity.notFound().build();
    }
}