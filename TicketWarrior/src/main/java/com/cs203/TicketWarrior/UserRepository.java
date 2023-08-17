package com.cs203.TicketWarrior;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserDTO, String> {
    UserDTO findByUsername(String username);
}
