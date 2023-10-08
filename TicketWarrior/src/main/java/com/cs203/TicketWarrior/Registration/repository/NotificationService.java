package com.cs203.TicketWarrior.Registration.repository;
import com.cs203.TicketWarrior.Registration.models.TestUser;
import org.springframework.mail.MailException;

public interface NotificationService {
    public void sendNotification(TestUser user) throws MailException;
}
