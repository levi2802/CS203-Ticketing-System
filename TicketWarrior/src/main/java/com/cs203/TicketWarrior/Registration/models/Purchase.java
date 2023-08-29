package com.cs203.TicketWarrior.Registration.models;

import java.util.List;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import lombok.Data;

@Data
public class Purchase {
    @Id
	private String id;
	@DBRef(db = "users")
	private String username;
	private String movieId;
	private List<String> SeatIds;
	private Date timestamp;
	// private PaymentMethod paymentMethod;
	// private Status status;
}
