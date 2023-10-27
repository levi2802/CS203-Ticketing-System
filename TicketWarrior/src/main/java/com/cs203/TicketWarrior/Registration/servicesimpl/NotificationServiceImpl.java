package com.cs203.TicketWarrior.Registration.servicesimpl;

import com.cs203.TicketWarrior.Registration.models.User;
import com.cs203.TicketWarrior.Registration.services.NotificationService;

import lombok.RequiredArgsConstructor;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@RequiredArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {
    private final JavaMailSender javaMailSender;

    public void sendNotification(User user, String message) throws MailException {
        // Send email
        SimpleMailMessage mail = new SimpleMailMessage();
        String newMessage = message + "\n\nTransaction done at " + LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a").withLocale(Locale.ENGLISH));

        mail.setTo(user.getEmail());
        mail.setFrom("wicketticketwarrior@gmail.com");
        mail.setSubject("Order Summary");
        mail.setText(newMessage);
        // mail.setText("Hi, " + user.getUsername() + "\nYour seats " + "[" +
        // selectedSeats + "]" + " for the movie: " + movieName + " are booked!");
        javaMailSender.send(mail);
    }
}