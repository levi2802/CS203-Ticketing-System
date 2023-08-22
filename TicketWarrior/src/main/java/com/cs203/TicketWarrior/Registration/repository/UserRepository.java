package com.cs203.TicketWarrior.Registration.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cs203.TicketWarrior.Registration.models.UserDTO;

public interface UserRepository extends MongoRepository<UserDTO, String> {
    
    UserDTO findByUsername(String username);

    Boolean usernameExists(String username);
}
