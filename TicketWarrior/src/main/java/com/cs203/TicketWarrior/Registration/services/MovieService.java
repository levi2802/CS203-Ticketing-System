package com.cs203.TicketWarrior.Registration.services;

import com.cs203.TicketWarrior.Registration.models.Movie;
import com.cs203.TicketWarrior.Registration.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class MovieService {
    @Autowired // Let the framework instantiate the interface.
    private MovieRepository movieRepository;
    public List<Movie> allMovies() {
        return movieRepository.findAll();
    }

    // Optional<Concert> to allow java to return null if concert does not exist.
    public Optional<Movie> singleMovie(String imdbId) {
        return movieRepository.findConcertByImdbId(imdbId);
    }
}
