package com.cs203.TicketWarrior.Registration.controllers;

import com.cs203.TicketWarrior.Registration.models.Seat;
import com.cs203.TicketWarrior.Registration.services.SeatService;
import com.cs203.TicketWarrior.Registration.Exceptions.OrderNotFoundException;
import com.cs203.TicketWarrior.Registration.Exceptions.SeatNotFoundException;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.hibernate.mapping.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/seats")
public class SeatController {
    private final SeatService seatService;

    @GetMapping
    public ResponseEntity<List<Seat>> getAllSeat() {
        return new ResponseEntity<List<Seat>>(seatService.allSeats(), HttpStatus.OK); // Sends Http status code 200.
    }

    @GetMapping("/{movieName}/{location}/{timing}")
    public ResponseEntity<List<Seat>> findAllOccupiedSeats(@PathVariable String movieName,
            @PathVariable String location, @PathVariable String timing) {
        return new ResponseEntity<List<Seat>>(seatService.findAllOccupiedSeats(movieName, location, timing),
                HttpStatus.OK);// Sends Http status
        // code 200.
    }

    @PostMapping
    public ResponseEntity<Seat> addSeat(@RequestBody Seat seat) throws Exception {
        return new ResponseEntity<Seat>(seatService.insert(seat), HttpStatus.OK);// Sends Http status code
                                                                                 // 200.
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<Seat>> findSeats(@PathVariable String username) {
        System.out.println(username);
        return new ResponseEntity<List<Seat>>(seatService.findbyID(username), HttpStatus.OK);// Sends Http status code
                                                                                             // 200.
    }

}