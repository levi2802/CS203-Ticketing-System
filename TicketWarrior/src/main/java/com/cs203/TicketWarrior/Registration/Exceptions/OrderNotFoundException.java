package com.cs203.TicketWarrior.Registration.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) // 404 Error
public class OrderNotFoundException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public OrderNotFoundException(String orderId) {
        super("Order not found with ID: " + orderId);
    }

}
