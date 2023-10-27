package com.cs203.TicketWarrior.Registration.controllers;

import com.cs203.TicketWarrior.Registration.models.User;
import com.cs203.TicketWarrior.Registration.services.NotificationService;
import com.cs203.TicketWarrior.Registration.services.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/mail")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MailController {
    private final NotificationService notificationService;
    private final UserService userService;

    @GetMapping("/{username}/{message}")
    public String sendMail(@PathVariable String username, @PathVariable String message) {
        Optional<User> optionalUser = userService.findUserByUsername(username);

        if (optionalUser.isEmpty()) {
            return "User not found";
        }

        User user = optionalUser.get();

        // Send a notification
        try {
            notificationService.sendNotification(user, message);
            return "Mail has been sent to " + user.getEmail();
        } catch (MailException e) {
            System.out.println("Could not send mail to " + user.getEmail() + e.getMessage());
        }

        return "Oops, something bad happened";
    }
}