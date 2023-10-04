package com.cs203.TicketWarrior.Registration.services;

import com.cs203.TicketWarrior.Registration.models.Seat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.cs203.TicketWarrior.Registration.repository.SeatRepository;
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

    public List<Seat> findAllOccupiedSeats(String movieName) {
        return SeatRepository.findAllOccupiedSeats(movieName);
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
