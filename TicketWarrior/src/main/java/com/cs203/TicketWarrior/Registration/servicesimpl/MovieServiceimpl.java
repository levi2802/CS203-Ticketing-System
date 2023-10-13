package com.cs203.TicketWarrior.Registration.servicesimpl;

import com.cs203.TicketWarrior.Registration.models.Movie;
import com.cs203.TicketWarrior.Registration.repository.MovieRepository;
import com.cs203.TicketWarrior.Registration.services.MovieService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;

@RequiredArgsConstructor
@Service
public class MovieServiceimpl implements MovieService{

    private final MovieRepository movieRepository;
    public List<Movie> allMovies() {
        return movieRepository.findAll();
    }

    // Optional<Concert> to allow java to return null if concert does not exist.
    public Optional<Movie> singleMovie(String imdbId) {
        return movieRepository.findConcertByImdbId(imdbId);
    }
}
