package com.cs203.TicketWarrior.Registration.models;

import java.util.Collection;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
// @Entity
@Document(collection = "users")
public class User implements UserDetails {

    @Id
    private String id;

    @NonNull
    @Email(message = "Please provide a valid email")
    private String email;

    @NonNull
    private String username;

    @NonNull
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "Password must be at least 8 characters long and contain at least one letter and one number")
    private String password;

    // A user can have 0 to many orders. We choose to cascade deletions, and delete
    // orphaned orders.
    // @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval =
    // true)
    // private List<Order> orders;

    // Declare as enum, using String to represent instead of integer
    // @Enumerated(EnumType.STRING)
    private ERole role;

    public User(String email, String username, String password) {
        this.email = email;
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
}
