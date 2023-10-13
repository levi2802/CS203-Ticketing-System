package com.cs203.TicketWarrior.Registration.services;

import java.util.List;
import java.util.Optional;

import com.cs203.TicketWarrior.Registration.models.MovieScreening;

public interface MovieScreeningService {
    public List<MovieScreening> allMovieScreening();
    public MovieScreening insert(MovieScreening moviescreening) throws Exception;
    public Optional<MovieScreening> singleMovieScreening(String imdbId);
}
