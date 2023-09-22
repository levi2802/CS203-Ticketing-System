package com.cs203.TicketWarrior.Registration.repository;

import java.util.List;

import com.cs203.TicketWarrior.Registration.models.Seat;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SeatRepository extends MongoRepository<Seat, String> {
    // Define your custom query method to find all occupied seats
    List<Seat> findByOccupiedTrue();
}
