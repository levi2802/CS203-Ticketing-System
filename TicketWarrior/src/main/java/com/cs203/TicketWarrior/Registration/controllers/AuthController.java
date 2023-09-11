package com.cs203.TicketWarrior.Registration.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cs203.TicketWarrior.Registration.models.User;
import com.cs203.TicketWarrior.Registration.payload.MessageResponse;
import com.cs203.TicketWarrior.Registration.payload.RegisterRequest;
import com.cs203.TicketWarrior.Registration.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    UserService userService;


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

        

        
        // // Create user
        // UserDTO user = new UserDTO(registerRequest.getUsername(), registerRequest.getPassword());

        // //Roles stuff

        // //Generate ID and assign to user

        // // Save created user into repository
        // userRepository.save(user);

        // return ResponseEntity.ok(new MessageResponse("User registered!"));
        
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
