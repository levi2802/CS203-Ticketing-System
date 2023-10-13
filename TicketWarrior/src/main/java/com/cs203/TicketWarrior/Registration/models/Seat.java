package com.cs203.TicketWarrior.Registration.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Document(collection = "seats") // Document containing mongodb collection.
@Data // From the lombok framework, takes care of all the getters, setters etc.
@AllArgsConstructor // From the lombok framework, creates a constructor that takes all the
                    // parameters.
@NoArgsConstructor // From the lombok framework, creates a constructor that takes no parameters.
public class Seat {
    @Id
    private String id;

//    @ManyToOne
//    // the column "order_id" will be in the auto-generated table "seats"
//    // nullable = false: add not-null constraint to the database column "user_id"
//    @JoinColumn(name = "order_id", nullable = false)
//    private Order order; // Multiple seats can belong to the same order.

    private int row;
    private int column;
    private String type;
    private boolean availability;
    private String username;
    private String movieName;
    private String location;
    private String timing;
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

    public void Updateall(int row, int coloumn, String type, boolean availability) {
        this.row = row;
        this.column = coloumn;
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
