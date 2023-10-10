package com.cs203.TicketWarrior.Registration.repository;

import com.cs203.TicketWarrior.Registration.models.Seat;
import com.cs203.TicketWarrior.Registration.models.User;
import com.cs203.TicketWarrior.Registration.repository.SeatRepository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public class SeatRepositoryImpl implements SeatRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public SeatRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<Seat> findAllOccupiedSeats(String movieName, String location, String timing) {
        // TODO Auto-generated method stub
        final Query query = new Query();
        //query.addCriteria(Criteria.where("availability").is(false));
        query.addCriteria(Criteria.where("movieName").is(movieName));
        query.addCriteria(Criteria.where("location").is(location));
        query.addCriteria(Criteria.where("timing").is(timing));
        return mongoTemplate.find(query, Seat.class);
        // throw new UnsupportedOperationException("Unimplemented method
        // 'findAllOccupiedSeats'");
    }

    public Seat findbySeat(Seat seat) {
        final Query query = new Query();
        query.addCriteria(Criteria.where("row").is(seat.getRow()));
        query.addCriteria(Criteria.where("coloumn").is(seat.getColoumn()));
        query.addCriteria(Criteria.where("movieName").is(seat.getMovieName()));
        query.addCriteria(Criteria.where("location").is(seat.getLocation()));
        query.addCriteria(Criteria.where("timing").is(seat.getMovieName()));
        return mongoTemplate.findOne(query, Seat.class);
    }

    // Find a seat with a matching seatId to the input seatId.
    public Seat findById(String seatId) {
        final Query query = new Query();
        query.addCriteria(Criteria.where("id").is(seatId));

        return mongoTemplate.findOne(query, Seat.class);
    }

    public List<Seat> findbyusername(String username){
        final Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));
        return  mongoTemplate.find(query, Seat.class);
    }
}
