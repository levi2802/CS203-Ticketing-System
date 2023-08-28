package kh.dev.movies.Movies;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reviews")
@Data // From the lombok framework, takes care of all the getters, setters etc.
@AllArgsConstructor // From the lombok framework, creates a constructor that takes all the parameters.
@NoArgsConstructor // From the lombok framework, creates a constructor that takes no parameters.
public class Review {
    @Id
    private ObjectId id;
    private String body;

    // Constructor for the body only

    public Review(String body) {
        this.body = body;
    }
}
