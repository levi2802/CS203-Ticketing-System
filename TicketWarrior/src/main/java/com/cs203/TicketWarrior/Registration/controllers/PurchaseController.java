package com.cs203.TicketWarrior.Registration.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.cs203.TicketWarrior.Registration.models.Purchase;
import com.cs203.TicketWarrior.Registration.services.PurchaseService;

@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {
    
    @Autowired
    private PurchaseService purchaseService;

    @GetMapping("/")
    public List<Purchase> getAllPurchases() {
        return purchaseService.findAll();
    }

    @GetMapping("/{userId}")
    public List<Purchase> getPurchasesByUser(@PathVariable String userId) {
        return purchaseService.findByUserId(userId);
    }

    @PostMapping("/")
    public Purchase createPurchase(@RequestBody Purchase purchase) {
        return purchaseService.createPurchase(purchase);
    }
    
}
