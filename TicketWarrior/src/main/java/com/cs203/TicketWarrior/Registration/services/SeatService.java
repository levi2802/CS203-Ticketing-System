package com.cs203.TicketWarrior.Registration.services;

import com.cs203.TicketWarrior.Registration.models.Movie;
import com.cs203.TicketWarrior.Registration.models.Seat;
import com.cs203.TicketWarrior.Registration.models.Order;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import com.cs203.TicketWarrior.Registration.repository.SeatRepository;
import com.cs203.TicketWarrior.Registration.repository.SeatRepositoryImpl;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SeatService {

    private final SeatRepository SeatRepository;


    public List<Seat> allSeats() {
        return SeatRepository.findAll();
    }

    public Seat insert(Seat seat) throws Exception {
        Seat foundSeat = SeatRepository.findbySeat(seat);
        if (foundSeat != null) {
            throw new Exception("entity already exist");
        }
        Seat insertedSeat = SeatRepository.insert(seat);

        return insertedSeat;
    }

    public List<Seat> findAllOccupiedSeats() {
        return SeatRepository.findAllOccupiedSeats();
    }

    public Optional<Seat> findSeatById(String seatId) {
        return SeatRepository.findById(seatId);
    }

    public Seat updateSeat(Seat seat) {
        return SeatRepository.save(seat);
    }
    public List<Seat> findbyID(String user_id){
        return SeatRepository.findbyusername(user_id);
        
    }

    // public List<Seat> findAvailableSeats() {
    // return SeatRepository.findAllAvailableSeats();
    // }

    // public List<Seat> saveAll(List<Seat> seats) {
    // return SeatRepository.saveAll(seats);
    // }

}
