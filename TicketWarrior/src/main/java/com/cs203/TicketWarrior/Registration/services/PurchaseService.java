package com.cs203.TicketWarrior.Registration.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDateTime;

import com.cs203.TicketWarrior.Registration.models.*;
import com.cs203.TicketWarrior.Registration.repository.*;

@RequiredArgsConstructor
@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;

    public List<Purchase> findAll() {
        return purchaseRepository.findAll();
    }

    public List<Purchase> findByUsername(String username) {
        return purchaseRepository.findByUsername(username);
    }

    public Purchase createPurchase(Purchase purchase) {
        // check if all seat ids in the purchase are available
        // List<String> seatIds = purchase.getSeatIds();
        // List<Seat> seats = seatRepository.findAllById(seatIds);
        // if (seats.stream().anyMatch(seat -> !seat.isAvailable())) {
        // throw new SeatNotAvailableException("One or more seats are not available");
        // }

        // update the seat status to not available
        // seats.forEach(seat -> seat.setAvailability(false));
        // seatRepository.saveAll(seats);

        // save the purchase
        purchase.setTimeStamp(LocalDateTime.now());
        return purchaseRepository.save(purchase);
    }

    // other methods...
}
