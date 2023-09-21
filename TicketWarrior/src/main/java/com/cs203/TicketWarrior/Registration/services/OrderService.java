package com.cs203.TicketWarrior.Registration.services;

import com.cs203.TicketWarrior.Registration.models.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cs203.TicketWarrior.Registration.repository.OrderRepository;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orders;

    public OrderService(OrderRepository orders) {
        this.orders = orders;
    }

    public List<Order> listOrders() {
        return orders.findAll();
    }

    public Order getOrder(String orderId, String userId) {
        return orders.findByIdAndUserId(orderId, userId).map(order -> {
            return order;
        }).orElse(null);
    }

    public List<Order> getOrdersByUser(String userId) {
        return orders.findByUserId(userId);
    }

    public Order addOrder(Order order) {
        return orders.save(order);
    }

    public Order updateOrder(String id, Order newOrderInfo) {
        return orders.findById(id).map(order -> {
            order.setUser(newOrderInfo.getUser());
            return orders.save(order);
        }).orElse(null);
    }

    public void deleteOrder(String id) {
        orders.deleteById(id);
    }
}
