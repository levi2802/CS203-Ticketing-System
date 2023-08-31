package com.cs203.TicketWarrior.Registration.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import com.cs203.TicketWarrior.Registration.models.Seat;
import com.cs203.TicketWarrior.Registration.repository.SeatRepository;

@Service
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;

    public List<Seat> findAll() {
        return seatRepository.findAll();
    }

    public List<Seat> findAvailableSeats() {
        List<Seat> allSeats = seatRepository.findAll();
        return allSeats.stream().filter(Seat::isAvailable).collect(Collectors.toList());
    }

    public List<Seat> saveAll(List<Seat> seats) {
        return seatRepository.saveAll(seats);
    }

    // other methods...
}
