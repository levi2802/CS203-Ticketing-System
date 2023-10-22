package com.cs203.TicketWarrior.Registration.repository;

import com.cs203.TicketWarrior.Registration.models.Movie;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface MovieRepository extends MongoRepository<Movie, ObjectId> {
    // Method to find by imdbid instead of the object id.
    // Can form dynamic queries like this using any property name in your model class as long as they are unqiue.
    Optional<Movie> findConcertByImdbId(String imdbId);
}
