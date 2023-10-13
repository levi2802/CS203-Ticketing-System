package com.cs203.TicketWarrior.Registration.repository;

import com.cs203.TicketWarrior.Registration.models.Seat;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface SeatRepository extends MongoRepository<Seat, String>, SeatRepositoryCustom {

    String mongoTemplate = null;
}
