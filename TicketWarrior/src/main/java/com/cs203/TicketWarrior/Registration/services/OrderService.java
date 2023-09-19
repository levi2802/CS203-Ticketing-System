package com.cs203.TicketWarrior.Registration.services;

import com.cs203.TicketWarrior.Registration.Exceptions.OrderNotFoundException;
import com.cs203.TicketWarrior.Registration.models.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cs203.TicketWarrior.Registration.repository.OrderRepository;

import java.util.ArrayList;
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

    public List<String> getOccupiedSeatsByMovieId(String movieName) {
        List<Order> orders = OrderRepository.findByMovieName(movieName);
        List<String> occupiedSeats = new ArrayList<>();
        for (Order order : orders) {
            occupiedSeats.addAll(order.getSeats());
        }
        return occupiedSeats;
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
