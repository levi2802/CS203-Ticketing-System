package com.cs203.TicketWarrior.Registration.services;

import com.cs203.TicketWarrior.Registration.models.User;
import com.cs203.TicketWarrior.Registration.servicesimpl.NotificationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.MailException;
import org.springframework.mail.MailPreparationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class NotificationServiceTest {

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private NotificationServiceImpl notificationServiceImpl;

    @BeforeEach
    void setUp() {
        // Initialize mocks and inject mocks
        MockitoAnnotations.openMocks(this);

        // Since this is a test, we do not want to send any actual mail
        doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));
    }

    // Test Case when sending notification is successful
    @Test
    void testSendNotification_Success() throws MailException {
        // Arrange
        User user = new User("testMail@gmail.com", "testMail", "testPassword1");

        // Act
        notificationServiceImpl.sendNotification(user, "testMessage");

        // Assert
        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    // Test Case when sending notification is not successful
    @Test
    void testSendNotification_Fail() throws MailException {
        // Arrange
        User user = new User("testMail@gmail.com", "testMail", "testPassword1");
        doThrow(new MailPreparationException("Failed")).when(javaMailSender).send(any(SimpleMailMessage.class));

        // Act & Assert
        assertThrows(MailException.class, () -> notificationServiceImpl.sendNotification(user, "testMessage"));
    }
}
