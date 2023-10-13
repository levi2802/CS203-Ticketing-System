package com.cs203.TicketWarrior.Registration.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    private String token;

    private String message;

    private Boolean isSuccessful;

    private String email;
}
