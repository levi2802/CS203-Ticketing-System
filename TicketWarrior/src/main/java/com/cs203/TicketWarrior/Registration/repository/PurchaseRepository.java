package com.cs203.TicketWarrior.Registration.repository;

import com.cs203.TicketWarrior.Registration.models.Purchase;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PurchaseRepository extends MongoRepository<Purchase, String> {

    List<Purchase> findByUserId(String userId);

    Optional<Purchase> findByIdAndUserId(String id, String userId);
}
