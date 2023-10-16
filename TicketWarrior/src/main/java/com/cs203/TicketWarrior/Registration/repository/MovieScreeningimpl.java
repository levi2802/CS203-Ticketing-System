package com.cs203.TicketWarrior.Registration.repository;

import com.cs203.TicketWarrior.Registration.models.MovieScreening;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.time.LocalDateTime;

public class MovieScreeningimpl {
    private final MongoTemplate mongoTemplate;
    
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
