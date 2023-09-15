package com.cs203.TicketWarrior.Registration.services;

import com.cs203.TicketWarrior.Registration.models.Seat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cs203.TicketWarrior.Registration.repository.SeatRepository;
import java.util.List;

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


    // public List<Seat> findAvailableSeats() {
    //     return SeatRepository.findAllAvailableSeats();
    // }

    // public List<Seat> saveAll(List<Seat> seats) {
    //     return SeatRepository.saveAll(seats);
    // }

}
