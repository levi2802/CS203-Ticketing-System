package com.cs203.TicketWarrior.Registration.services;

import com.cs203.TicketWarrior.Registration.models.User;
import com.cs203.TicketWarrior.Registration.repository.NotificationService;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {
    private final JavaMailSender javaMailSender;

    @Override
    public void sendNotification(User user, String movieName, String selectedSeats) throws MailException {
        // Send email
        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setTo(user.getEmail());
        mail.setFrom("wicketticketwarrior@gmail.com");
        mail.setSubject("Order Summary");
        //mail.setText("Hi, " + user.getUsername() + "\nYour seats " + "[" + selectedSeats + "]" + " for the movie: " + movieName + " are booked!");
        javaMailSender.send(mail);
    }
    
    public void sendNotification(User user, String message) throws MailException {
        // Send email
        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setTo(user.getEmail());
        mail.setFrom("wicketticketwarrior@gmail.com");
        mail.setSubject("Order Summary");
        mail.setText(message);
        //mail.setText("Hi, " + user.getUsername() + "\nYour seats " + "[" + selectedSeats + "]" + " for the movie: " + movieName + " are booked!");
        javaMailSender.send(mail);
    }
}