package com.cs203.TicketWarrior.Registration.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

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
