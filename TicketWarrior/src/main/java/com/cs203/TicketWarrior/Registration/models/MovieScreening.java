package com.cs203.TicketWarrior.Registration.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.JoinColumn;

import java.time.LocalDateTime;
import java.util.*;

@Document(collection = "moviescreening") // Document containing mongodb collection.
@Data // From the lombok framework, takes care of all the getters, setters etc.
@AllArgsConstructor // From the lombok framework, creates a constructor that takes all the
                    // parameters.
@NoArgsConstructor // From the lombok framework, creates a constructor that takes no parameters.
public class MovieScreening {
    @Id
    private String movieScreenid;
    @NotBlank
    private String movieName;
    @NotBlank
    private LocalDateTime movieScreenTime;
    @NotBlank
    private double priceScale; 

}
