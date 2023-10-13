package com.cs203.TicketWarrior.Registration.servicesimpl;
import com.cs203.TicketWarrior.Registration.models.MovieScreening;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.cs203.TicketWarrior.Registration.repository.MovieScreeningRepository;
import com.cs203.TicketWarrior.Registration.services.MovieScreeningService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MovieScreeningServiceimpl implements MovieScreeningService{

    private final MovieScreeningRepository MovieScreeningRepository;

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
