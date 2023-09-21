package com.cs203.TicketWarrior.Registration.services;
import com.cs203.TicketWarrior.Registration.models.MovieScreening;
import com.cs203.TicketWarrior.Registration.models.Movie;
import com.cs203.TicketWarrior.Registration.models.Seat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import com.cs203.TicketWarrior.Registration.repository.MovieScreeningRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MovieScreeningService {
    @Autowired
    private MovieScreeningRepository MovieScreeningRepository;

    public MovieScreeningService(MovieScreeningRepository MovieScreeningRepository) {
        this.MovieScreeningRepository = MovieScreeningRepository;
    }

    public List<MovieScreening> allMovieScreening() {
        return MovieScreeningRepository.findAll();
    }

    public MovieScreening insert(MovieScreening moviescreening) throws Exception {
        // MovieScreening foundMovieScreening = MovieScreeningRepository.findMovieScreening(moviescreening);
        // if(foundMovieScreening != null){
        //     throw new Exception("entity already exist");
        // }
        MovieScreeningRepository.insert(moviescreening);
        return moviescreening;
    }

    public Optional<MovieScreening> singleMovieScreening(String imdbId) {
        //unimplemented atm
        return null;
    }
}
