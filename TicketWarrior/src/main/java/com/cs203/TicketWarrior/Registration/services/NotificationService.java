package com.cs203.TicketWarrior.Registration.services;

import com.cs203.TicketWarrior.Registration.models.User;
import org.springframework.mail.MailException;

public interface NotificationService {
    public void sendNotification(User user, String message) throws MailException;
}
