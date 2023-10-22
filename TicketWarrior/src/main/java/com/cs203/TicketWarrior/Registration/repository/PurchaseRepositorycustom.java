package com.cs203.TicketWarrior.Registration.repository;

import java.util.List;

import com.cs203.TicketWarrior.Registration.models.Purchase;

public interface PurchaseRepositorycustom {
    List<Purchase> findByUserId(String username);
}
