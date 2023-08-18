package com.cs203.TicketWarrior.Registration.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.cs203.TicketWarrior.Registration.models.UserDTO;
import com.cs203.TicketWarrior.Registration.payload.MessageResponse;
import com.cs203.TicketWarrior.Registration.payload.RegisterRequest;
import com.cs203.TicketWarrior.Registration.repository.RoleRepository;
import com.cs203.TicketWarrior.Registration.repository.UserRepository;

import ch.qos.logback.core.model.Model;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    // @Valid perform validation based on the constraints defined in RegisterRequest.java
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {

        //Checks if username exists through a method from the UserRepository class
        if(userRepository.usernameExists(registerRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username already taken!"));
        }

        // Create user
        
    }
}
