package com.cs203.TicketWarrior.Registration.controllers;

import com.cs203.TicketWarrior.Registration.models.TestUser;
import com.cs203.TicketWarrior.Registration.models.User;
import com.cs203.TicketWarrior.Registration.repository.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailController {
    private final NotificationService notificationService;

    @Autowired
    public MailController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/send")
    public String sendMail() {
        TestUser user = new TestUser();
        user.setEmail("www.levijr@gmail.com");
        user.setUsername("test");

        // Send a notification
        try {
            notificationService.sendNotification(user);
            return "mail has been sent.";
        } catch (MailException e) {
            System.out.println("Could not send mail to " + user.getUsername() + e.getMessage());
        }

        return "Oops, something bad happened";
    }

}
