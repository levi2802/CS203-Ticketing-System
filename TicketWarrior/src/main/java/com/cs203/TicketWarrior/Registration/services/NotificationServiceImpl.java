package com.cs203.TicketWarrior.Registration.services;

import com.cs203.TicketWarrior.Registration.models.Order;
import com.cs203.TicketWarrior.Registration.models.TestUser;
import com.cs203.TicketWarrior.Registration.repository.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final JavaMailSender javaMailSender;
    private final OrderService orderService;

    @Autowired
    public NotificationServiceImpl (JavaMailSender javaMailSender, OrderService orderService) {
        this.javaMailSender = javaMailSender;
        this.orderService = orderService;
    }

    @Override
    public void sendNotification(TestUser user) throws MailException {
//        List<Order> orders = orderService.getOrdersByUser(user.getId());

        // Send email
        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setTo(user.getEmail());
        mail.setFrom("kenghiang3e42016@gmail.com");
        mail.setSubject("Order Summary");
        mail.setText("Hi, " + user.getUsername() + "\nHere are your bookings!\n\n");
        javaMailSender.send(mail);
    }
}
