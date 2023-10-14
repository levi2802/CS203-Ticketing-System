package com.cs203.TicketWarrior.Registration.controllers;

import java.util.Optional;

import com.cs203.TicketWarrior.Registration.models.User;
import com.cs203.TicketWarrior.Registration.services.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    // Get User by Username
    @GetMapping("/{username}")
    public ResponseEntity<Optional<User>> getUserByUsername(@PathVariable(value = "username") String username) {
        return new ResponseEntity<Optional<User>>(userService.findUserByUsername(username), HttpStatus.OK);
    }

}
