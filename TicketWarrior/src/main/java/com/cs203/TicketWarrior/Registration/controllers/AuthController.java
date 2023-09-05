package com.cs203.TicketWarrior.Registration.controllers;

import com.cs203.TicketWarrior.Registration.payload.LoginRequest;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.cs203.TicketWarrior.Registration.models.User;
import com.cs203.TicketWarrior.Registration.payload.MessageResponse;
import com.cs203.TicketWarrior.Registration.payload.RegisterRequest;
import com.cs203.TicketWarrior.Registration.repository.RoleRepository;
import com.cs203.TicketWarrior.Registration.repository.UserRepository;
import com.cs203.TicketWarrior.Registration.services.UserService;

import ch.qos.logback.core.model.Model;
import jakarta.validation.Valid;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    // @Valid perform validation based on the constraints defined in RegisterRequest.java
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {

        //Checks if username exists through a method from the UserRepository class
        if(userService.doesUsernameExist(registerRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username already taken!"));
        }

        User user = new User(registerRequest.getUsername(), registerRequest.getPassword());

        userService.save(user);

        return new ResponseEntity<User>(user, HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate
                (new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        Optional<User> user = userService.findUserByUsername(authentication.getName());
        return new ResponseEntity<User>(user.get(), HttpStatus.ACCEPTED);
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
