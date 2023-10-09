package com.cs203.TicketWarrior.Registration.controllers;

import com.cs203.TicketWarrior.Registration.models.User;
import com.cs203.TicketWarrior.Registration.repository.NotificationService;
import com.cs203.TicketWarrior.Registration.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/mail")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MailController {
    private final NotificationService notificationService;
    private  final UserService userService;

    @GetMapping("/send/{username}")
    public String sendMail(@PathVariable String username) {
        Optional<User> optionalUser = userService.findUserByUsername(username);

        if (optionalUser.isEmpty()) {
            return "User not found";
        }

        User user = optionalUser.get();

        // Send a notification
        try {
            notificationService.sendNotification(user);
            return "Mail has been sent to " + user.getEmail();
        } catch (MailException e) {
            System.out.println("Could not send mail to " + user.getEmail() + e.getMessage());
        }

        return "Oops, something bad happened";
    }

}