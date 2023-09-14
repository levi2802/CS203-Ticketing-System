package com.cs203.TicketWarrior.Registration.models;

<<<<<<< HEAD
import java.util.Collection;
import java.util.List;

import lombok.*;
=======
import java.util.HashSet;
import java.util.Set;
import java.util.List; // import the List class
>>>>>>> orderHistorySprint2
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

<<<<<<< HEAD

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@Entity
@Document(collection = "users")
public class User implements UserDetails {
=======
import jakarta.persistence.CascadeType; // import jakarta persistence classes
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;

// This is the User Data Transfer Object. It is used to send all of the registration information to our Spring Backend.
@Document(collection = "users")
public class User {
>>>>>>> orderHistorySprint2

    @Id
    private String id;

    @NonNull
    private String username;

    @NonNull
    private String password;

<<<<<<< HEAD
    // Declare as enum, using String to represent instead of integer
//    @Enumerated(EnumType.STRING)
    private ERole role;
=======
    @DBRef
    private Set<Role> roles = new HashSet<>();

    // Users can have multiple orders.
    // "user" is what is used in the Order class to map the orders to the user.
    // orders cannot exist without their respective user.
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;

    public User() {

    }
>>>>>>> orderHistorySprint2

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }
<<<<<<< HEAD
}
=======

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
>>>>>>> orderHistorySprint2
