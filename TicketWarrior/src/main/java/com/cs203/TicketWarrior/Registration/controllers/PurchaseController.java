package com.cs203.TicketWarrior.Registration.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.cs203.TicketWarrior.Registration.models.Purchase;
import com.cs203.TicketWarrior.Registration.services.PurchaseService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/purchases")
public class PurchaseController {
    
    private final PurchaseService purchaseService;

    @GetMapping
    public List<Purchase> getAllPurchases() {
        return purchaseService.findAll();
    }

    @GetMapping("/{userId}")
    public List<Purchase> getPurchasesByUser(@PathVariable String userId) {
        return purchaseService.findByUserId(userId);
    }

    @PostMapping("/postPurchase")
    public Purchase createPurchase(@RequestBody Purchase purchase) {
        System.out.println(purchase.getSeatIDs());
        return purchaseService.createPurchase(purchase);
    }
    
}
