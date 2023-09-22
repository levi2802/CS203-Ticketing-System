package com.cs203.TicketWarrior.Registration.models;

import java.util.ArrayList;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime; // Imports the LocalDateTime class. 

import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn; // import the JoinColumn class

import lombok.*; // import lombok classes

@Getter // Automatically creates getters
@Setter // Automatically creates setters
@ToString // Automatically adds ToString method
@AllArgsConstructor // Automatically adds constructor taking in all arguments
@NoArgsConstructor // Automatically adds constructor taking in no arguments
// This is an Order object.
@Document(collection = "orders")
public class Order {

    @Id
    private String id; // Every order has its own id.

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Multiple orders can belong to the same user.

    @NotBlank
    private LocalDateTime purchaseDateTime; // Every order has a purchase date and time.

    // @NotBlank
    // private Movie movie; // Every order is for a movie. (movie name)

    @NotBlank
    private String movieName;

    // @NotBlank
    // private LocalDateTime moviescreentime;

    @NotBlank
    private ArrayList<String> seats; // Every order has a number of seats attached to it. {[a1,a2,a3]}

    // // Get the IDs of the seats booked for this order, in string form.
    // public List<String> getSeatIDs() {
    //     List<String> SeatIds = new ArrayList<String>();

    //     for (Seat seat : seats) {
    //         SeatIds.add(seat.getId());
    //     }

    //     return SeatIds;
    // }

    // // Add seat to this order's list of seats
    // public Seat addSeat(Seat seat) {
    //     this.seats.add(seat);
    //     return seat;
    // }
}
