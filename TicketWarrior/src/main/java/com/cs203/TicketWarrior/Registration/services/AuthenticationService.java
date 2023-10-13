package com.cs203.TicketWarrior.Registration.services;

import com.cs203.TicketWarrior.Registration.payload.AuthenticationRequest;
import com.cs203.TicketWarrior.Registration.payload.AuthenticationResponse;

public interface AuthenticationService {
    public AuthenticationResponse register(AuthenticationRequest registerRequest);
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
}
