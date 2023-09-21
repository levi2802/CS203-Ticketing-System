package com.cs203.TicketWarrior.Registration.controllers;

import com.cs203.TicketWarrior.Registration.models.Movie;
import com.cs203.TicketWarrior.Registration.models.Seat;
import com.cs203.TicketWarrior.Registration.models.Order;

import com.cs203.TicketWarrior.Registration.services.MovieService;
import com.cs203.TicketWarrior.Registration.services.SeatService;
import com.cs203.TicketWarrior.Registration.services.OrderService;
import com.cs203.TicketWarrior.Registration.Exceptions.OrderNotFoundException;
import com.cs203.TicketWarrior.Registration.Exceptions.SeatNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/seats")
public class SeatController {
    @Autowired
    private SeatService seatService;

    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<List<Seat>> getAllSeat() {
        return new ResponseEntity<List<Seat>>(seatService.allSeats(), HttpStatus.OK); // Sends Http status code 200.
    }

    @GetMapping("/OccupiedSeats")
    public ResponseEntity<List<Seat>> findAllOccupiedSeats() {
        return new ResponseEntity<List<Seat>>(seatService.findAllOccupiedSeats(), HttpStatus.OK);// Sends Http status
                                                                                                 // code 200.
    }

    @PostMapping("/PostSeats")
    public ResponseEntity<Seat> addSeat(@RequestBody Seat seat) throws Exception {
        return new ResponseEntity<Seat>(seatService.insert(seat), HttpStatus.OK);// Sends Http status code
                                                                                 // 200.
    }
    @GetMapping("/findSeats/{username}")
    public ResponseEntity<List<Seat>> findSeats(@PathVariable String username){
        System.out.println(username);
        return new ResponseEntity<List<Seat>>(seatService.findbyID(username), HttpStatus.OK);// Sends Http status code 200.
    }

    // Links order and seat together
    @PutMapping("/users/{userId}/LinkOrderAndSeat/{seatId}")
    public Seat linkOrderAndSeat(@PathVariable(value = "userId") String userId,
            @PathVariable(value = "seatId") String seatId, @RequestBody Order order) {

        // Exceptions if the order and the seat do not exist
        if (orderService.getOrder(order.getId(), userId) == null) {
            throw new OrderNotFoundException(userId, order.getId());
        }
        if (!seatService.findSeatById(seatId).isPresent()) {
            throw new SeatNotFoundException(seatId);
        }

        // Set order of the seat
        Seat existingSeat = seatService.findSeatById(seatId).get();

        existingSeat.UpdateOrderOfSeat(order);
        seatService.updateSeat(existingSeat);

        // Add seat to the order
        Order existingOrder = orderService.getOrder(order.getId(), userId);

        existingOrder.addSeat(existingSeat);
        orderService.updateOrder(existingOrder.getId(), existingOrder);

        return existingSeat;
    }
}