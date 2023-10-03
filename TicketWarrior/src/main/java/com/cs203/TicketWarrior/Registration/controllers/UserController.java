package com.cs203.TicketWarrior.Registration.controllers;

import java.util.Optional;

import com.cs203.TicketWarrior.Registration.services.UserService;

import com.cs203.TicketWarrior.Registration.models.User;
import com.cs203.TicketWarrior.Registration.Exceptions.*;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    // Get User by Username
    @GetMapping("/getUserByUsername")
    public Optional<User> getUserByUsername(@RequestBody String username) {
        return userService.findUserByUsername(username);
    }

}
