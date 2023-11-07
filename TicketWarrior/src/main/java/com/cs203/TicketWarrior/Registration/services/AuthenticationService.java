package com.cs203.TicketWarrior.Registration.services;

import com.cs203.TicketWarrior.Registration.payload.AuthenticationRequest;
import com.cs203.TicketWarrior.Registration.payload.AuthenticationResponse;
import com.cs203.TicketWarrior.Registration.payload.RegisterRequest;

public interface AuthenticationService {
    public AuthenticationResponse register(RegisterRequest registerRequest);
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
}
