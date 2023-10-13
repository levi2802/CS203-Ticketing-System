package com.cs203.TicketWarrior.Registration.services;

import java.util.List;

import com.cs203.TicketWarrior.Registration.models.Purchase;

public interface PurchaseService {
    public List<Purchase> listPurchases();
    public Purchase getPurchase(String purchaseId, String userId);
    public List<Purchase> getPurchasesByUser(String userId);
    public Purchase addPurchase(Purchase purchase);
    public void deletePurchase(String id);
}
