package com.cs203.TicketWarrior.Registration.repository;
import com.cs203.TicketWarrior.Registration.models.User;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;

public interface NotificationService {
    public void sendNotification(User user) throws MailException;
}
