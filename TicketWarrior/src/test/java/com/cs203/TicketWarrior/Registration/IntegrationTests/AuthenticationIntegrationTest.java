package com.cs203.TicketWarrior.Registration.IntegrationTests;

import com.cs203.TicketWarrior.Registration.models.User;
import com.cs203.TicketWarrior.Registration.payload.AuthenticationRequest;
import com.cs203.TicketWarrior.Registration.payload.AuthenticationResponse;
import com.cs203.TicketWarrior.Registration.payload.RegisterRequest;
import com.cs203.TicketWarrior.Registration.repository.UserRepository;
import com.cs203.TicketWarrior.Registration.services.AuthenticationService;
import com.cs203.TicketWarrior.Registration.servicesimpl.AuthenticationServiceimpl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration"
})
public class AuthenticationIntegrationTest {

    @LocalServerPort
    private int port;

    private final String baseUrl = "http://localhost:";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        // clear the database after each test
        userRepository.deleteAll();
    }

    @Test
    public void testRegisterUser_Success() throws Exception {
        // Arrange
        // Register user
        AuthenticationRequest request = new AuthenticationRequest("validUser@gmail.com", "validUser", "goodpassword1");

        // Set URI
        URI uri = new URI(baseUrl + port + "/api/v1/auth/registration");

        // Set token in http header
        HttpEntity<?> entity = new HttpEntity<>(request);

        // Act
        // Call API
        ResponseEntity<AuthenticationResponse> result = testRestTemplate.exchange(uri, HttpMethod.POST, entity,
                AuthenticationResponse.class);
        AuthenticationResponse response = result.getBody();

        // Assert
        assertEquals(200, result.getStatusCode().value());
        assertTrue(response.getIsSuccessful());
    }

    @Test
    public void testRegisterUser_Fail() throws Exception {
        // Arrange
        User registeredUser = new User("validUser@gmail.com", "validUser", "goodpassword1");
        userRepository.save(registeredUser);
        // Register user
        AuthenticationRequest request = new AuthenticationRequest("validUser@gmail.com", "validUser", "goodpassword1");

        // Set URI
        URI uri = new URI(baseUrl + port + "/api/v1/auth/registration");

        // Set token in http header
        HttpEntity<?> entity = new HttpEntity<>(request);

        // Act
        // Call API
        ResponseEntity<AuthenticationResponse> result = testRestTemplate.exchange(uri, HttpMethod.POST, entity,
                AuthenticationResponse.class);
        AuthenticationResponse response = result.getBody();

        // Assert
        assertEquals(200, result.getStatusCode().value());
        assertFalse(response.getIsSuccessful());
    }

    @Test
    public void testAuthenticateUser_Success() throws Exception {
        // Arrange
        RegisterRequest registerRequest = new RegisterRequest("validUser@gmail.com", "validUser",
                "goodpassword1");
        AuthenticationResponse registerResponse = authenticationService.register(registerRequest);
        AuthenticationRequest request = new AuthenticationRequest("null", "validUser", "goodpassword1");

        // Set URI
        URI uri = new URI(baseUrl + port + "/api/v1/auth/authentication");

        // Set token in http header
        HttpEntity<?> entity = new HttpEntity<>(request);

        // Act
        // Call API
        ResponseEntity<AuthenticationResponse> result = testRestTemplate.exchange(uri, HttpMethod.POST, entity,
                AuthenticationResponse.class);
        AuthenticationResponse response = result.getBody();

        // Assert
        assertEquals(200, result.getStatusCode().value());
        assertTrue(response.getIsSuccessful());
    }

    @Test
    public void testAuthenticateUser_Fail() throws Exception {
        // Arrange
        RegisterRequest registerRequest = new RegisterRequest("validUser@gmail.com", "validUser",
                "goodpassword1");
        AuthenticationResponse registerResponse = authenticationService.register(registerRequest);
        AuthenticationRequest request = new AuthenticationRequest("null", "validUser", "badpassword1");

        // Set URI
        URI uri = new URI(baseUrl + port + "/api/v1/auth/authentication");

        // Set token in http header
        HttpEntity<?> entity = new HttpEntity<>(request);

        // Act
        // Call API
        ResponseEntity<AuthenticationResponse> result = testRestTemplate.exchange(uri, HttpMethod.POST, entity,
                AuthenticationResponse.class);
        AuthenticationResponse response = result.getBody();

        // Assert
        assertEquals(200, result.getStatusCode().value());
        assertFalse(response.getIsSuccessful());
    }
}
