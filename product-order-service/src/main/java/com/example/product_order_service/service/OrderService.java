package com.example.product_order_service.service;

import com.example.product_order_service.entity.Order;
import com.example.product_order_service.repo.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    public Order createOrder(Order order) {
        return orderRepo.save(order);
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepo.findById(id);
    }

    public List<Order> getAllOrdersOfUser(String userName) {
        return orderRepo.findByUserName(userName);
    }

    public Order updateOrder(Long id, Order order) {
        Optional<Order> existingOrder = orderRepo.findById(id);
        if (existingOrder.isPresent()) {
            order.setId(id);
            return orderRepo.save(order);
        }
        return null;
    }

    public boolean deleteOrder(Long id) {
        if (orderRepo.existsById(id)) {
            orderRepo.deleteById(id);
            return true;
        }
        return false;
    }
}