package com.cs203.TicketWarrior.Registration.repository;

import com.cs203.TicketWarrior.Registration.models.Seat;
import com.cs203.TicketWarrior.Registration.models.User;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface SeatRepository extends MongoRepository<Seat, String>, SeatRepositoryCustom {

    String mongoTemplate = null;
    

}
