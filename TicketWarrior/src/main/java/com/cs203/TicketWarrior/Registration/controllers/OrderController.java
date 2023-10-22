package com.cs203.TicketWarrior.Registration.controllers;

import java.util.List;

import com.cs203.TicketWarrior.Registration.services.OrderService;
import com.cs203.TicketWarrior.Registration.services.UserService;
import com.cs203.TicketWarrior.Registration.models.Order;
import com.cs203.TicketWarrior.Registration.Exceptions.*;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

    // Get all orders made by user
    @GetMapping("/users/{userId}/orders")
    public List<Order> getAllOrdersByUserId(@PathVariable(value = "userId") String userId) {

        return orderService.getOrdersByUser(userId);
    }

    // Get specific order using userId and orderId
    @GetMapping("/users/{userId}/orders/{orderId}")
    public Order getOrderByUserIdAndOrderId(@PathVariable(value = "userId") String userId,
            @PathVariable(value = "orderId") String orderId) {

        return orderService.getOrder(orderId, userId);
    }

    // Post a new order
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users/{userId}/orders")
    public Order addorder(@PathVariable(value = "userId") String userId, @RequestBody Order order) {
        if (userService.doesUserIdExist(userId)) {
            return orderService.addOrder(order);
        }
        throw new UserNotFoundException(userId);
    }

    // Update an existing order
    @PutMapping("/users/{userId}/orders/{orderId}")
    public Order updateOrder(@PathVariable(value = "userId") String userId,
            @PathVariable(value = "orderId") String orderId,
            @RequestBody Order newOrderInfo) {
        if (orderService.getOrder(orderId, userId) == null) {
            throw new OrderNotFoundException(userId, orderId);
        }
        Order order = orderService.updateOrder(orderId, newOrderInfo);
        return order;
    }

    // Delete an existing order
    @DeleteMapping("/users/{userId}/orders/{orderId}")
    public void deleteOrder(@PathVariable(value = "userId") String userId,
            @PathVariable(value = "orderId") String orderId) {
        if (orderService.getOrder(orderId, userId) == null) {
            throw new OrderNotFoundException(userId, orderId);
        }
        orderService.deleteOrder(orderId);
    }
}
