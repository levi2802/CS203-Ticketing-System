package com.cs203.TicketWarrior.Registration.repository;

import java.util.List;

import com.cs203.TicketWarrior.Registration.models.Seat;

public interface SeatRepositoryCustom {
    List<Seat> findAllOccupiedSeats(String movieName, String location, String timing, String movieDate);

    Seat findbySeat(Seat seat);
    List<Seat> findbyusername(String user_id);
}
