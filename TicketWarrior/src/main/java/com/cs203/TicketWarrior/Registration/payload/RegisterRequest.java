package com.cs203.TicketWarrior.Registration.payload;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {


    @NotBlank
    private String username;

    @NotBlank
    private String password;

}
