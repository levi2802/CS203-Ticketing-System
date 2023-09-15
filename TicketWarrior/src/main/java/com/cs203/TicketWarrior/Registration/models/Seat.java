package com.cs203.TicketWarrior.Registration.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
    // private float price;
	// private SeatType seatType;

    public void Updateall(int row, int coloumn, String type, Boolean availability){
        this.row = row;
        this.coloumn = coloumn;
        this.type = type;
        this.availability = availability;
    }

    public boolean isAvailable() {
        return this.availability;
    }
}

