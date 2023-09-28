package com.cs203.TicketWarrior.Registration.models;

import java.util.List;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "purchases")
public class Purchase {
    @Id
	private String id;
	private String userId;
	private String movieId;
	private List<String> seatIDs;
	private LocalDateTime timestamp;
	// private PaymentMethod paymentMethod;
	// private Status status;

	public Purchase(String userId, String movieId, List<String> seatIDs) {
		this.userId = userId;
		this.movieId = movieId;
		this.seatIDs = seatIDs;
	}

	public void setTimeStamp(LocalDateTime timestamp){
		this.timestamp = timestamp;
	}
}
