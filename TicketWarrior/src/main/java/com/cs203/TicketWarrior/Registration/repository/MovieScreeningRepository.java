package com.cs203.TicketWarrior.Registration.repository;

import com.cs203.TicketWarrior.Registration.models.MovieScreening;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface MovieScreeningRepository extends MongoRepository<MovieScreening, String>, MovieScreeningCustom{
    
}
