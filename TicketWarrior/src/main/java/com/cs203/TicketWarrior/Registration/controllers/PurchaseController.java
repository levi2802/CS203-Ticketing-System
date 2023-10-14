package com.cs203.TicketWarrior.Registration.controllers;

import java.util.List;

import com.cs203.TicketWarrior.Registration.models.Purchase; // Make sure to update this
import com.cs203.TicketWarrior.Registration.services.PurchaseService;
import com.cs203.TicketWarrior.Registration.Exceptions.*;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/purchases")
public class PurchaseController {
    private final PurchaseService purchaseService;

    @GetMapping
    public List<Purchase> getAllPurchases() {
        return purchaseService.listPurchases();
    }

    // Get all purchases made by user
    @GetMapping("/{userId}")
    public List<Purchase> getAllPurchasesByUserId(@PathVariable(value = "userId") String userId) {
        return purchaseService.getPurchasesByUser(userId);
    }

    // Get specific purchase using userId and purchaseId
    @GetMapping("/{userId}/{purchaseId}")
    public Purchase getPurchaseByUserIdAndPurchaseId(@PathVariable(value = "userId") String userId,
            @PathVariable(value = "purchaseId") String purchaseId) {
        return purchaseService.getPurchase(purchaseId, userId);
    }

    // Post a new purchase
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Purchase addPurchase(@RequestBody Purchase purchase) {
        return purchaseService.addPurchase(purchase);
    }

    // // Update an existing purchase
    // @PutMapping("/users/{userId}/purchases/{purchaseId}")
    // public Purchase updatePurchase(@PathVariable(value = "userId") String userId,
    // @PathVariable(value = "purchaseId") String purchaseId,
    // @RequestBody Purchase newPurchaseInfo) {
    // if (purchaseService.getPurchase(purchaseId, userId) == null) {
    // throw new PurchaseNotFoundException(userId, purchaseId); // Update this
    // exception too
    // }
    // return purchaseService.updatePurchase(purchaseId, newPurchaseInfo);
    // }

    // Delete an existing purchase
    @DeleteMapping("/{userId}/{purchaseId}")
    public void deletePurchase(@PathVariable(value = "userId") String userId,
            @PathVariable(value = "purchaseId") String purchaseId) {
        if (purchaseService.getPurchase(purchaseId, userId) == null) {
            throw new PurchaseNotFoundException(userId, purchaseId); // Update this exception too
        }
        purchaseService.deletePurchase(purchaseId);
    }
}
