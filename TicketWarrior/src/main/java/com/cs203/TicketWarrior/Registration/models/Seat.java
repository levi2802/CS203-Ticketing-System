package com.cs203.TicketWarrior.Registration.models;

import org.springframework.data.annotation.Id;
import lombok.Data;

@Data
public class Seat {
    @Id
	private String id;
	private int row;
	private int column;
	private boolean isAvailable;
	// private float price;
	// private SeatType seatType;
}
