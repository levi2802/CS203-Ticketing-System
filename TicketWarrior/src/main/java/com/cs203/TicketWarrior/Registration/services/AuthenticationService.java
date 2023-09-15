package com.cs203.TicketWarrior.Registration.services;

import com.cs203.TicketWarrior.Registration.jwt.JwtService;
import com.cs203.TicketWarrior.Registration.models.ERole;
import com.cs203.TicketWarrior.Registration.models.User;
import com.cs203.TicketWarrior.Registration.payload.AuthenticationRequest;
import com.cs203.TicketWarrior.Registration.payload.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    // Can be userdetailsservice instead
    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(AuthenticationRequest registerRequest) {

        if (userService.doesUsernameExist(registerRequest.getUsername()) || userService.doesEmailExist(registerRequest.getEmail())) {
            return AuthenticationResponse.builder()
                    .message("Username or email already taken.")
                    .isSuccessful(false)
                    .build();
        }

        var user = User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(ERole.USER)
                .build();

        userService.save(user);

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .message("Success! Account Registered!")
                .isSuccessful(true)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {


        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()
                    )
            );

            var user = userService.findUserByUsername(authenticationRequest.getUsername())
                    .orElseThrow();

            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .message("Success!")
                    .isSuccessful(true)
                    .build();
        } catch(BadCredentialsException error) {
            return AuthenticationResponse.builder()
                    .token(null)
                    .message("Fail!")
                    .isSuccessful(false)
                    .build();
        }
    }
}
