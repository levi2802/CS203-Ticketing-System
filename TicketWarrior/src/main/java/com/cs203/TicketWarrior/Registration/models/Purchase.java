package com.cs203.TicketWarrior.Registration.models;

import java.util.List;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import lombok.Data;

@Data
public class Purchase {
    @Id
	private String id;
	private String userId;
	private String movieId;
	private List<String> seatIds;
	private LocalDateTime timestamp;
	// private PaymentMethod paymentMethod;
	// private Status status;
}
