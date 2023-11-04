package com.cs203.TicketWarrior.Registration.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "purchases")
public class Purchase {
	@Id
	private String id;

	@NotBlank
	private String userId;

	@NotBlank
	private String movieId;

	@NotBlank
	private String seatIDs;

	@Pattern(regexp = "\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}:\\d{2} [APMapm]{2}")
	private String timestamp;

	@NotBlank
	private String location;

	@NotBlank
	private String timing;

	@NotBlank
	private String movieDate;

	@Positive
	private int price;
	// private PaymentMethod paymentMethod;
	// private Status status;

	public Purchase(String username, String movieId, String seatIDs) {
		this.userId = username;
		this.movieId = movieId;
		this.seatIDs = seatIDs;
	}

	public void setTimeStamp(String timestamp) {
		this.timestamp = timestamp;
	}
}
