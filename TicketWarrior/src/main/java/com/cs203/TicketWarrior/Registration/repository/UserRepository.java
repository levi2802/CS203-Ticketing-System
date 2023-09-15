package com.cs203.TicketWarrior.Registration.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.cs203.TicketWarrior.Registration.models.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    // Implement the mongo repository findUserByUsername method, return an optional object in case
    // user is not found
    Optional<User> findUserByUsername(String username);

    Optional<User> findUserByEmail(String email);
}



