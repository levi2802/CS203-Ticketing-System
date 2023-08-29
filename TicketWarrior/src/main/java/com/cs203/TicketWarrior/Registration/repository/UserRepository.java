package com.cs203.TicketWarrior.Registration.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.cs203.TicketWarrior.Registration.models.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findUserByUsername(String username);
}



