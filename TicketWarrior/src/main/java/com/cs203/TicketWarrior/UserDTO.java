package com.cs203.TicketWarrior;

import jakarta.validation.constraints.NotBlank;


// This is the User Data Transfer Object. It is used to send all of the registration information to our Spring Backend.
public class UserDTO {
    
    @NotBlank
    private String username;

    @NotBlank
    private String password;

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
}
