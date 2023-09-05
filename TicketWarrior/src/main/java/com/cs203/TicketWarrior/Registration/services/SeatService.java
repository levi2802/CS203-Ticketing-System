package com.cs203.TicketWarrior.Registration.services;

import com.cs203.TicketWarrior.Registration.models.Movie;
import com.cs203.TicketWarrior.Registration.models.Seat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import com.cs203.TicketWarrior.Registration.repository.SeatRepository;
import com.cs203.TicketWarrior.Registration.repository.SeatRepositoryImpl;

import java.util.List;
import java.util.Optional;

@Service
public class SeatService{
    @Autowired
    private SeatRepository SeatRepository;

    public SeatService(SeatRepository SeatRepository) {
        this.SeatRepository = SeatRepository;
    }

    public List<Seat> allSeats() {
        return SeatRepository.findAll();
    }

    // public Optional<Seat> findAllOccupiedSeat() {
    //     Optional<Seat> seat = SeatRepository.findAllOccupiedSeats(true);
    //     return seat;
    // }

    public Seat insert(Seat seat) throws Exception {
        Seat foundSeat = SeatRepository.findbySeat(seat);
        if(foundSeat != null){
            throw new Exception("entity already exist");
        }
        SeatRepository.insert(seat);
        return seat;
    }

    public List<Seat> findAllOccupiedSeats(){
        return SeatRepository.findAllOccupiedSeats();
        
    }
}
