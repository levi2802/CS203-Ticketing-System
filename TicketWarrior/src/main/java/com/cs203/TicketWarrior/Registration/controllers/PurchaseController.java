package com.cs203.TicketWarrior.Registration.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import com.cs203.TicketWarrior.Registration.models.Purchase;
import com.cs203.TicketWarrior.Registration.services.PurchaseService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;

    @GetMapping
    public List<Purchase> getAllPurchases() {
        return purchaseService.findAll();
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<Purchase>> getPurchasesByUsername(@PathVariable String username) {
        return new ResponseEntity<List<Purchase>>(purchaseService.findByUsername(username), HttpStatus.OK);
    }

    @PostMapping("/postPurchase")
    public Purchase createPurchase(@RequestBody Purchase purchase) {
        System.out.println(purchase.getSeatIDs());
        return purchaseService.createPurchase(purchase);
    }

}
