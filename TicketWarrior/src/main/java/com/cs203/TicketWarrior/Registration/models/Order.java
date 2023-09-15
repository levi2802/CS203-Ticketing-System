package com.cs203.TicketWarrior.Registration.models;

import java.util.ArrayList;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime; // Imports the LocalDateTime class. 

import jakarta.persistence.ManyToOne;
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

    @NotBlank
    private Movie movie; // Every order is for a movie.

    @NotBlank
    private ArrayList<String> seats; // Every order has a number of seats attached to it.

    @NotBlank
    private int totalPrice; // Every order has a total price.
}
