package com.cs203.TicketWarrior.Registration.controllers;

import java.util.List;

import com.cs203.TicketWarrior.Registration.services.PurchaseService;
import com.cs203.TicketWarrior.Registration.services.UserService;
import com.cs203.TicketWarrior.Registration.models.Purchase; // Make sure to update this
import com.cs203.TicketWarrior.Registration.Exceptions.*;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class PurchaseController {
    private final PurchaseService purchaseService;
    private final UserService userService;

    // Get all purchases made by user
    @GetMapping("/users/{userId}/purchases")
    public List<Purchase> getAllPurchasesByUserId(@PathVariable(value = "userId") String userId) {
        return purchaseService.getPurchasesByUser(userId);
    }

    // Get specific purchase using userId and purchaseId
    @GetMapping("/users/{userId}/purchases/{purchaseId}")
    public Purchase getPurchaseByUserIdAndPurchaseId(@PathVariable(value = "userId") String userId,
                                                     @PathVariable(value = "purchaseId") String purchaseId) {
        return purchaseService.getPurchase(purchaseId, userId);
    }

    // Post a new purchase
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users/{userId}/purchases")
    public Purchase addPurchase(@PathVariable(value = "userId") String userId, @RequestBody Purchase purchase) {
        if (userService.doesUserIdExist(userId)) {
            return purchaseService.addPurchase(purchase);
        }
        throw new UserNotFoundException(userId);
    }

    // // Update an existing purchase
    // @PutMapping("/users/{userId}/purchases/{purchaseId}")
    // public Purchase updatePurchase(@PathVariable(value = "userId") String userId,
    //                                @PathVariable(value = "purchaseId") String purchaseId,
    //                                @RequestBody Purchase newPurchaseInfo) {
    //     if (purchaseService.getPurchase(purchaseId, userId) == null) {
    //         throw new PurchaseNotFoundException(userId, purchaseId); // Update this exception too
    //     }
    //     return purchaseService.updatePurchase(purchaseId, newPurchaseInfo);
    // }

    // Delete an existing purchase
    @DeleteMapping("/users/{userId}/purchases/{purchaseId}")
    public void deletePurchase(@PathVariable(value = "userId") String userId,
                               @PathVariable(value = "purchaseId") String purchaseId) {
        if (purchaseService.getPurchase(purchaseId, userId) == null) {
            throw new PurchaseNotFoundException(userId, purchaseId); // Update this exception too
        }
        purchaseService.deletePurchase(purchaseId);
    }
}
