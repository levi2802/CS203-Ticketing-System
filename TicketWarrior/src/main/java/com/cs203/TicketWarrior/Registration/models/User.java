package com.cs203.TicketWarrior.Registration.models;

import java.util.HashSet;
import java.util.Set;
import java.util.List; // import the List class
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;

import jakarta.persistence.CascadeType; // import jakarta persistence classes
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;

// This is the User Data Transfer Object. It is used to send all of the registration information to our Spring Backend.
@Document(collection = "users")
public class User {

    @Id
    private String id;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @DBRef
    private Set<Role> roles = new HashSet<>();

    // Users can have multiple orders.
    // "user" is what is used in the Order class to map the orders to the user.
    // orders cannot exist without their respective user.
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;

    public User() {

    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    // Getters and setters for User Orders
    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
