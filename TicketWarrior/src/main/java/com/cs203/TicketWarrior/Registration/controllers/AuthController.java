package com.cs203.TicketWarrior.Registration.controllers;

import com.cs203.TicketWarrior.Registration.payload.*;
import com.cs203.TicketWarrior.Registration.services.AuthenticationService;

import com.cs203.TicketWarrior.Registration.servicesimpl.AuthenticationServiceimpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    // @Valid perform validation based on the constraints defined in
    // RegisterRequest.java
    @PostMapping("/registration")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {

        return ResponseEntity.ok(authenticationService.register(registerRequest));

        // //Checks if username exists through a method from the UserRepository class
        // if(userService.doesUsernameExist(registerRequest.getUsername())) {
        // return ResponseEntity.badRequest().body(new MessageResponse("Error: Username
        // already taken!"));
        // }
        
        // User user = new User(registerRequest.getUsername(),
        // registerRequest.getPassword());
        
        // userService.save(user);
        
        // return new ResponseEntity<User>(user, HttpStatus.CREATED);

    }

    @PostMapping("/authentication")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @Valid @RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }

    @GetMapping("/test")
    public String test() {
        return "Hello world";
    }
}

// 1. User registers at the front end page
// 2. Payload request is created and send to the controller
// 3. Controller uses service/repository to check if username exists
// 4. If pass, creates a new User model class
