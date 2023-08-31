package com.cs203.TicketWarrior.Registration.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.cs203.TicketWarrior.Registration.models.Seat;
import com.cs203.TicketWarrior.Registration.services.SeatService;

@RestController
@RequestMapping("/api/seats")
public class SeatController {
    
    @Autowired
    private SeatService seatService;

    @GetMapping("/")
    public List<Seat> getAllSeats() {
        return seatService.findAll();
    }

    @GetMapping("/available")
    public List<Seat> getAvailableSeats() {
        return seatService.findAvailableSeats();
    }

    @PostMapping("/")
    public List<Seat> createOrUpdateSeats(@RequestBody List<Seat> seats) {
        return seatService.saveAll(seats);
    }

    // other methods...
}
