package com.cs203.TicketWarrior.Registration.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import com.cs203.TicketWarrior.Registration.models.*;
import com.cs203.TicketWarrior.Registration.repository.*;
import com.cs203.TicketWarrior.Registration.exceptions.SeatNotAvailableException;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private SeatRepository seatRepository;

    public List<Purchase> findAll() {
        return purchaseRepository.findAll();
    }

    public List<Purchase> findByUserId(String userId) {
        return purchaseRepository.findAll().stream()
                .filter(purchase -> purchase.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public Purchase createPurchase(Purchase purchase) {
        // check if all seat ids in the purchase are available
        List<String> seatIds = purchase.getSeatIds();
        List<Seat> seats = seatRepository.findAllById(seatIds);
        if (seats.stream().anyMatch(seat -> !seat.isAvailable())) {
            throw new SeatNotAvailableException("One or more seats are not available");
        }

        // update the seat status to not available
        seats.forEach(seat -> seat.setAvailable(false));
        seatRepository.saveAll(seats);

        // save the purchase
        return purchaseRepository.save(purchase);
    }

    // other methods...
}
