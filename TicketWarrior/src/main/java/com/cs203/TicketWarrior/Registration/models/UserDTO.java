package com.cs203.TicketWarrior.Registration.models;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;




// This is the User Data Transfer Object. It is used to send all of the registration information to our Spring Backend.
@Document(collection = "users")
public class UserDTO{

    @Id
    private String id;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @DBRef
    private Set<Role> roles = new HashSet<>();

    public UserDTO() {

    }

    public UserDTO(String username, String password) {
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
}
