package com.cs203.TicketWarrior.Registration.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "seats") // Document containing mongodb collection.
@Data // From the lombok framework, takes care of all the getters, setters etc.
@AllArgsConstructor // From the lombok framework, creates a constructor that takes all the
                    // parameters.
@NoArgsConstructor // From the lombok framework, creates a constructor that takes no parameters.
public class Seat {
    @Id
    private String id;
    private int row;
    private int column;
    private String type;
    private Boolean occupied;
    private String userId;
    // private String movieScreenId;
    // private float price;
    // private SeatType seatType;

    public void Updateall(int row, int column, String type, Boolean occupied) {
        this.row = row;
        this.column = column;
        this.type = type;
        this.occupied = occupied;
    }

    public boolean isAvailable() {
        return this.occupied;
    }
}
