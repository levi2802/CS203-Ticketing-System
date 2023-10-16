package com.cs203.TicketWarrior.Registration.servicesimpl;

import com.cs203.TicketWarrior.Registration.models.Purchase;
import com.cs203.TicketWarrior.Registration.repository.PurchaseRepository;
import com.cs203.TicketWarrior.Registration.services.PurchaseService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Service
public class PurchaseServiceimpl implements PurchaseService{

    private final PurchaseRepository purchases;

    public List<Purchase> listPurchases() {
        return purchases.findAll();
    }

    public Purchase getPurchase(String purchaseId, String userId) {
        return purchases.findByIdAndUserId(purchaseId, userId).orElse(null);
    }

    public List<Purchase> getPurchasesByUser(String userId) {
        return purchases.findByUserId(userId);
    }

    public Purchase addPurchase(Purchase purchase) {
        purchase.setTimeStamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a").withLocale(Locale.ENGLISH)));
        return purchases.save(purchase);
    }

    // public Purchase updatePurchase(String id, Purchase newPurchaseInfo) {
    //     return purchases.findById(id).map(purchase -> {
    //         purchase.setUser(newPurchaseInfo.getUser());
    //         return purchases.save(purchase);
    //     }).orElse(null);
    // }

    public void deletePurchase(String id) {
        purchases.deleteById(id);
    }
}
