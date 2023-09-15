package com.cs203.TicketWarrior.Registration.repository;

import com.cs203.TicketWarrior.Registration.models.Seat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import java.util.List;

public class SeatRepositoryImpl implements SeatRepositoryCustom {

    
    private final MongoTemplate mongoTemplate;
    
    @Autowired
    public SeatRepositoryImpl(MongoTemplate mongoTemplate){
        this.mongoTemplate = mongoTemplate;
    }
    public List<Seat> findAllOccupiedSeats() {
        // TODO Auto-generated method stub
        final Query query = new Query();
        query.addCriteria(Criteria.where("availability").is(false));
        return mongoTemplate.find(query, Seat.class);
        //throw new UnsupportedOperationException("Unimplemented method 'findAllOccupiedSeats'");
    }

    public Seat findbySeat(Seat seat){
        final Query query = new Query();
        query.addCriteria(Criteria.where("row").is(seat.getRow()));
        query.addCriteria(Criteria.where("coloumn").is(seat.getColoumn()));
        return  mongoTemplate.findOne(query, Seat.class);
    }
}
