package com.cs203.TicketWarrior.Registration.services;

import com.cs203.TicketWarrior.Registration.Exceptions.OrderNotFoundException;
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

    public Order getOrder(String orderId) {
        return orders.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
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
            // You might also want to update other fields of the order here
            return orders.save(order);
        }).orElseThrow(() -> new OrderNotFoundException(id));
    }

    public void deleteOrder(String id) {
        orders.deleteById(id);
    }
}
