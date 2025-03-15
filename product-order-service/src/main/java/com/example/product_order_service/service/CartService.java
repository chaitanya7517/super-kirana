package com.example.product_order_service.service;

import com.example.product_order_service.entity.Cart;
import com.example.product_order_service.entity.CartItem;
import com.example.product_order_service.entity.Order;
import com.example.product_order_service.entity.OrderItem;
import com.example.product_order_service.repo.CartRepo;
import com.example.product_order_service.repo.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private OrderRepo orderRepo;

    public Cart addCart(Cart cart) {
        return cartRepo.save(cart);
    }

    public Optional<Cart> getCartById(Long id) {
        return cartRepo.findById(id);
    }

    public Cart updateCart(Long id, Cart cart) {
        Optional<Cart> existingCart = cartRepo.findById(id);
        if (existingCart.isPresent()) {
            return cartRepo.save(cart);
        }
        return null;
    }

    public boolean deleteCart(Long id) {
        if (cartRepo.existsById(id)) {
            cartRepo.deleteById(id);
            return true;
        }
        return false;
    }

    public Order checkoutCart(Long cartId) {
        Optional<Cart> cart = cartRepo.findById(cartId);
        if (cart.isPresent()) {
            Order order = new Order();
            order.setUserName(cart.get().getUserName());
            order.setTotalAmount(cart.get().getTotalAmount());
            List<CartItem> cartItems = cart.get().getItems();
            List<OrderItem> orderItems = cart.get().getItems().stream()
                    .map(cartItem -> {
                        OrderItem orderItem = new OrderItem();
                        orderItem.setQuantity(cartItem.getQuantity());
                        orderItem.setProduct(cartItem.getProduct());
                        orderItem.setPrice(cartItem.getPrice());
                        return orderItem;
                    })
                    .collect(Collectors.toList());

            order.setItems(orderItems);
            return  orderRepo.save(order);
        }
        return null;
    }
}