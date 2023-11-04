package com.cs203.TicketWarrior.Registration.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
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

    @NotBlank
    @Positive
    private int row;

    @NotBlank
    @Positive
    private int column;

    @NotBlank
    private String type;

    @NotBlank
    private boolean availability;

    @NotBlank
    private String username;

    @NotBlank
    private String movieName;

    @NotBlank
    private String location;

    @NotBlank
    private String timing;

    @NotBlank
    private String movieDate;
    // private float price;
    // private SeatType seatType;

    public Seat(int row, int column, String type, boolean availability, String username, String movieName) {
        this.row = row;
        this.column = column;
        this.type = type;
        this.availability = availability;
        this.username = username;
        this.movieName = movieName;
    }

    public void Updateall(int row, int column, String type, boolean availability) {
        this.row = row;
        this.column = column;
        this.type = type;
        this.availability = availability;
        //this.username = username;
    }

    // Method to update the order this seat is assigned to.
//    public void UpdateOrder(Order order) {
//        this.order = order;
//    }
//
//    // Link seat to input order
//    public Seat UpdateOrderOfSeat(Order order) {
//        this.order = order;
//        return this;
//    }


    public boolean isAvailable() {
        return this.availability;
    }
}
