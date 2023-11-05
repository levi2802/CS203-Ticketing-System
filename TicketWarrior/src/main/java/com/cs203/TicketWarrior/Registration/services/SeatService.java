package com.cs203.TicketWarrior.Registration.services;

import java.util.List;
import java.util.Optional;

import com.cs203.TicketWarrior.Registration.models.Seat;

public interface SeatService {
    public List<Seat> allSeats();
    public Seat insert(Seat seat) throws Exception;
    public List<Seat> findAllOccupiedSeats(String movieName, String location, String timing, String movieDate);
    public Optional<Seat> findSeatById(String seatId);
    public Seat updateSeat(Seat seat);
    public List<Seat> findbyID(String user_id);
}
