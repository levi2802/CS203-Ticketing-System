package com.cs203.TicketWarrior.Registration.controllers;

import com.cs203.TicketWarrior.Registration.models.Movie;
import com.cs203.TicketWarrior.Registration.models.MovieScreening;
import com.cs203.TicketWarrior.Registration.models.Seat;
import com.cs203.TicketWarrior.Registration.services.MovieScreeningService;
import com.cs203.TicketWarrior.Registration.services.SeatService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/moviescreenings")
public class MovieScreeningController{
    @Autowired
    private MovieScreeningService movieScreeningService;
    @GetMapping
    public ResponseEntity<List<MovieScreening>> getAllMoviesscreenings() {
        return new ResponseEntity<List<MovieScreening>>(movieScreeningService.allMovieScreening(), HttpStatus.OK); // Sends Http status code 200.
    }

    @GetMapping("/{MovieScreeningid}")
    public ResponseEntity<Optional<MovieScreening>> getSingleMovieScreening(@PathVariable String imdbId) {
        return new ResponseEntity<Optional<MovieScreening>>(movieScreeningService.singleMovieScreening(imdbId), HttpStatus.OK);
    }
}