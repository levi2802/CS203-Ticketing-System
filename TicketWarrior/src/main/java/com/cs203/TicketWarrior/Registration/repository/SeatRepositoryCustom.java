package com.cs203.TicketWarrior.Registration.repository;

import java.util.List;
import java.util.Optional;

import com.cs203.TicketWarrior.Registration.models.Seat;

public interface SeatRepositoryCustom {
    List<Seat> findAllOccupiedSeats();

    Seat findbySeat(Seat seat);
    List<Seat> findbyusername(String user_id);
}
