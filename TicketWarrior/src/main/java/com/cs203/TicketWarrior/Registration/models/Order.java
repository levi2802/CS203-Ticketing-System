package com.cs203.TicketWarrior.Registration.models;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime; // Imports the LocalDateTime class. 

import jakarta.persistence.CascadeType; // import jakarta persistence classes
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
    // the column "user_id" will be in the auto-generated table "orders"
    // nullable = false: add not-null constraint to the database column "user_id"
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Multiple orders can belong to the same user.

    @NotBlank
    private LocalDateTime purchaseDateTime; // Every order has a purchase date and time.

    // // NOTE: Uncomment this when MovieScreening class exists
    // @NotBlank
    // private MovieScreening MovieScreening; // Every order is for a movie.

    // An order can have 1 to many seats. We choose to cascade deletions, and delete
    // orphaned orders.
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Seat> seats;

    @NotBlank
    private double totalPrice; // Every order has a total price.

    // Get the IDs of the seats booked for this order, in string form.
    public List<String> getSeatIDs() {
        List<String> SeatIds = new ArrayList<String>();

        for (Seat seat : seats) {
            SeatIds.add(seat.getId());
        }

        return SeatIds;
    }

    // Add seat to this order's list of seats
    public Seat addSeat(Seat seat) {
        this.seats.add(seat);
        return seat;
    }
}
