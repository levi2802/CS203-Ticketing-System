package kh.dev.movies.Registration;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.*;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
}

