package com.cs203.TicketWarrior.Registration.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {

    @NotBlank
    private String email;
    @NotBlank
    private String username;
    @NotBlank
    private String password;

    public AuthenticationRequest (String username, String password) {
        this.username = username;
        this.password = password;
    }
}
