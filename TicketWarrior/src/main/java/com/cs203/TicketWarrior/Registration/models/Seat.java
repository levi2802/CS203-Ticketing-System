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

import java.util.*;

@Document(collection = "seats") // Document containing mongodb collection.
@Data // From the lombok framework, takes care of all the getters, setters etc.
@AllArgsConstructor // From the lombok framework, creates a constructor that takes all the parameters.
@NoArgsConstructor // From the lombok framework, creates a constructor that takes no parameters.
public class Seat {
     @Id
	private String id;
    private int row;
    private int coloumn;
    private String type;
    private Boolean availability;
    private String username;
    // private float price;
	// private SeatType seatType;

    public void Updateall(Order order, int row, int coloumn, String type, Boolean availability, String username) {
        //this.order = order;
        this.row = row;
        this.coloumn = coloumn;
        this.type = type;
        this.availability = availability;
        this.username = username;
    }

    // Method to update the order this seat is assigned to.
    public void UpdateOrder(Order order) {
        //this.order = order;
    }

    public boolean isAvailable() {
        return this.availability;
    }
}

