package com.cs203.TicketWarrior.Registration.repository;

import com.cs203.TicketWarrior.Registration.models.Seat;
import com.cs203.TicketWarrior.Registration.models.MovieScreening;
import com.cs203.TicketWarrior.Registration.repository.SeatRepository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class MovieScreeningimpl {
    private final MongoTemplate mongoTemplate;
    
    @Autowired
    public MovieScreeningimpl(MongoTemplate mongoTemplate){
        this.mongoTemplate = mongoTemplate;
    }
    public MovieScreening findMovieScreening(String movieName, LocalDateTime time) {
        // TODO Auto-generated method stub
        final Query query = new Query();
        query.addCriteria(Criteria.where("MovieName").is(movieName));
        query.addCriteria(Criteria.where("movieScreenTime").is(time));
        return mongoTemplate.findOne(query, MovieScreening.class);
        //throw new UnsupportedOperationException("Unimplemented method 'findAllOccupiedSeats'");
    }

    
}
