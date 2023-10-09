package com.cs203.TicketWarrior.Registration.services;

import com.cs203.TicketWarrior.Registration.models.Purchase;
import com.cs203.TicketWarrior.Registration.models.User;
import com.cs203.TicketWarrior.Registration.repository.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {
    private final JavaMailSender javaMailSender;
    private final PurchaseService purchaseService;

    @Override
    public void sendNotification(User user) throws MailException {
        String userId = user.getUsername();
        List<Purchase> purchases = purchaseService.findByUserId(userId);

        StringBuilder sb = new StringBuilder();
        for (Purchase p : purchases) {
            // Get seat data
            StringBuilder tmp = new StringBuilder();
            for (String seat : p.getSeatIDs()) {
                tmp.append(seat).append(",");
            }
            String seats = tmp.toString();

            // Get movie data
            String movie = p.getMovieId();

            // Create orders
            String orders = "(" + movie + ": " + "[" + seats + "]" + ")";
            sb.append(orders).append(",");
        }

        String res = sb.toString();

        System.out.println(res);

        // Send email
        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setTo(user.getEmail());
        mail.setFrom("kenghiang3e42016@gmail.com");
        mail.setSubject("Order Summary");
        mail.setText("Hi, " + user.getUsername() + "\nHere are your bookings!\n\n");
        javaMailSender.send(mail);
    }
}