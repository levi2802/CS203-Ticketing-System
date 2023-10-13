package com.cs203.TicketWarrior.Registration.IntegrationTests;

import com.cs203.TicketWarrior.Registration.models.Order;
import com.cs203.TicketWarrior.Registration.models.Purchase;
import com.cs203.TicketWarrior.Registration.models.Seat;
import com.cs203.TicketWarrior.Registration.models.User;
import com.cs203.TicketWarrior.Registration.payload.AuthenticationRequest;
import com.cs203.TicketWarrior.Registration.payload.AuthenticationResponse;
import com.cs203.TicketWarrior.Registration.repository.SeatRepository;
import com.cs203.TicketWarrior.Registration.repository.UserRepository;
import com.cs203.TicketWarrior.Registration.services.AuthenticationService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration"
})
public class SeatingIntegrationTest {

    @LocalServerPort
    private int port;

    private final String baseUrl = "http://localhost:";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        seatRepository.deleteAll();
        userRepository.deleteAll();
    }

    @AfterEach
    void tearDown(){
        // clear the database after each test
        seatRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testRegisterUser_Success() throws Exception {
        //Arrange
        //Register user
        AuthenticationRequest request = new AuthenticationRequest("testCalvin@gmail.com", "testCalvin", "goodpassword");
        AuthenticationResponse response = authenticationService.register(request);

        //Set URI
        URI uri = new URI(baseUrl + port + "/api/v1/seats/");

        //Save seats
        Order order = new Order();
        Seat seat = new Seat(1, 1, "standard", false, request.getUsername(), "Barbie");
        seatRepository.save(seat);

        //Set token in http header
        String token = response.getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        //Act
        //Call API
        ResponseEntity<List<Seat>> result = testRestTemplate.exchange(uri, HttpMethod.GET, entity, Seat[].class);
//        Seat[] resultResponse = result.getBody();

        //Assert
        assertEquals(200, result.getStatusCode().value());
//        assertEquals(seat, resultResponse);
    }
}
