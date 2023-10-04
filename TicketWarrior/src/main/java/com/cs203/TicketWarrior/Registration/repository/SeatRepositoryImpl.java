package com.cs203.TicketWarrior.Registration.repository;

import com.cs203.TicketWarrior.Registration.models.Seat;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import java.util.List;

public class SeatRepositoryImpl implements SeatRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    public SeatRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<Seat> findAllOccupiedSeats(String movieName) {
        // TODO Auto-generated method stub
        final Query query = new Query();
        query.addCriteria(Criteria.where("availability").is(false));
        query.addCriteria(Criteria.where("movieName").is(movieName));
        return mongoTemplate.find(query, Seat.class);
        // throw new UnsupportedOperationException("Unimplemented method
        // 'findAllOccupiedSeats'");
    }

    public Seat findbySeat(Seat seat) {
        final Query query = new Query();
        query.addCriteria(Criteria.where("row").is(seat.getRow()));
        query.addCriteria(Criteria.where("coloumn").is(seat.getColoumn()));
        query.addCriteria(Criteria.where("movieName").is(seat.getMovieName()));
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
