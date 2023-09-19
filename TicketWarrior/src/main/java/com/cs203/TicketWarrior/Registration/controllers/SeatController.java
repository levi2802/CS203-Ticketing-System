package com.cs203.TicketWarrior.Registration.controllers;

import com.cs203.TicketWarrior.Registration.models.Movie;
import com.cs203.TicketWarrior.Registration.models.Seat;
import com.cs203.TicketWarrior.Registration.services.MovieService;
import com.cs203.TicketWarrior.Registration.services.SeatService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/seats")
public class SeatController{
    @Autowired
    private SeatService seatService;
    @GetMapping
    public ResponseEntity<List<Seat>> getAllSeat() {
        return new ResponseEntity<List<Seat>>(seatService.allSeats(), HttpStatus.OK); // Sends Http status code 200.
    }
    
    @GetMapping("/OccupiedSeats")
    public ResponseEntity<List<Seat>> findAllOccupiedSeats(){
        return new ResponseEntity<List<Seat>>(seatService.findAllOccupiedSeats(), HttpStatus.OK);// Sends Http status code 200.
    }

    @PostMapping("/PostSeats")
    public ResponseEntity<Seat> addSeat(@RequestBody Seat seat) throws Exception{
        return new ResponseEntity<Seat>(seatService.insert(seat), HttpStatus.OK);// Sends Http status code 200.
    }
}