package com.cs203.TicketWarrior.Registration.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) // 404 Error
public class PurchaseNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PurchaseNotFoundException(String userId, String purchaseId) {
        super("Could not find purchase " + purchaseId + " made by user " + userId);
    }
}
