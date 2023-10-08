package com.cs203.TicketWarrior.Registration.IntegrationTests;

import com.cs203.TicketWarrior.Registration.payload.AuthenticationRequest;
import com.cs203.TicketWarrior.Registration.payload.AuthenticationResponse;
import com.cs203.TicketWarrior.Registration.repository.UserRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @AfterEach
    void tearDown(){
        // clear the database after each test
        userRepository.deleteAll();
    }

    @Test
    public void testRegisterUser_Success() throws Exception {
        //Arrange
        //Register user
        AuthenticationRequest request = new AuthenticationRequest("validUser@gmail.com", "validUser", "goodpassword");

        //Set URI
        URI uri = new URI(baseUrl + port + "/api/auth/register");

        //Set token in http header
        HttpEntity<?> entity = new HttpEntity<>(request);

        //Act
        //Call API
        ResponseEntity<AuthenticationResponse> result = testRestTemplate.exchange(uri, HttpMethod.POST, entity, AuthenticationResponse.class);
        AuthenticationResponse response = result.getBody();

        //Assert
        assertEquals(200, result.getStatusCode().value());
        assertTrue(response.getIsSuccessful());
    }
}
