package com.cs203.TicketWarrior.Registration.services;

import java.util.List;
import java.util.Optional;

import com.cs203.TicketWarrior.Registration.models.Movie;

public interface MovieService {
    public List<Movie> allMovies();
    public Optional<Movie> singleMovie(String imdbId);
}
