package com.cs203.TicketWarrior.Registration.IntegrationTests;

import com.cs203.TicketWarrior.Registration.models.User;
import com.cs203.TicketWarrior.Registration.payload.AuthenticationRequest;
import com.cs203.TicketWarrior.Registration.payload.AuthenticationResponse;
import com.cs203.TicketWarrior.Registration.payload.RegisterRequest;
import com.cs203.TicketWarrior.Registration.repository.PurchaseRepository;
import com.cs203.TicketWarrior.Registration.repository.UserRepository;
import com.cs203.TicketWarrior.Registration.services.UserService;
import com.cs203.TicketWarrior.Registration.servicesimpl.AuthenticationServiceimpl;
import com.cs203.TicketWarrior.Registration.servicesimpl.NotificationServiceImpl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration"
})
public class MailIntegrationTest {

    @LocalServerPort
    private int port;

    private final String baseUrl = "http://localhost:";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private AuthenticationServiceimpl authenticationService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private UserService userService;

    @Autowired
    @Mock
    private NotificationServiceImpl notificationService;

    @BeforeEach
    void setUp() {

        notificationService = Mockito.mock(NotificationServiceImpl.class);
        doNothing().when(notificationService).sendNotification(any(User.class), any(String.class));
        // doNothing().when(notificationService).sendNotification(any(User.class),
        // any(String.class), any(String.class));
        userRepository.deleteAll();
    }
    // @BeforeEach
    // void setUp () {
    // // Initialize mocks and inject mocks
    // MockitoAnnotations.openMocks(this);
    //
    // // Since this is a test, we do not want to send any actual mail
    //
    // }

    @AfterEach
    void tearDown() {
        // clear the database after each test
        userRepository.deleteAll();
    }

    @Test
    public void testSendMail_InvalidUsername_ReturnUserNotFound() throws Exception {
        // Arrange
        // Register user
        RegisterRequest request = new RegisterRequest("isaaclokeweiensecondary@gmail.com", "testCalvin",
                "goodpassword1");
        AuthenticationResponse response = authenticationService.register(request);

        // Set token in http header
        String token = response.getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        URI uri = new URI(baseUrl + port + "/api/v1/mail/" + "invalidUsername/testMessage");
        ResponseEntity<String> result = testRestTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        String resultMessage = result.getBody();

        System.out.println(result.toString());

        // assertEquals(404, result.getStatusCode().value());
        assertTrue(resultMessage.equals("User not found"));
    }

    @Test
    public void testSendMail_UsernameAndMessage_ReturnSuccessMessage() throws Exception {
        // Arrange
        // Register user
        RegisterRequest request = new RegisterRequest("isaaclokeweiensecondary@gmail.com", "testCalvin",
                "goodpassword1");
        AuthenticationResponse response = authenticationService.register(request);

        // Set token in http header
        String token = response.getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        URI uri = new URI(baseUrl + port + "/api/v1/mail/" + "testCalvin/testMessage");
        ResponseEntity<String> result = testRestTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        String resultMessage = result.getBody();

        System.out.println(resultMessage);

        // assertEquals(404, result.getStatusCode().value());
        assertTrue(resultMessage.equals("Mail has been sent to isaaclokeweiensecondary@gmail.com"));
    }
}
