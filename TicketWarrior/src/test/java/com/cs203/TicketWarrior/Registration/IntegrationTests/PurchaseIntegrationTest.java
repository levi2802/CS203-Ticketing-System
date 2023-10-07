package com.cs203.TicketWarrior.Registration.IntegrationTests;

import com.cs203.TicketWarrior.Registration.jwt.JwtService;
import com.cs203.TicketWarrior.Registration.models.Purchase;
import com.cs203.TicketWarrior.Registration.models.User;
import com.cs203.TicketWarrior.Registration.payload.AuthenticationRequest;
import com.cs203.TicketWarrior.Registration.payload.AuthenticationResponse;
import com.cs203.TicketWarrior.Registration.repository.PurchaseRepository;
import com.cs203.TicketWarrior.Registration.repository.UserRepository;
import com.cs203.TicketWarrior.Registration.services.AuthenticationService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration"
})
public class PurchaseIntegrationTest {

    @LocalServerPort
    private int port;

    private final String baseUrl = "http://localhost:";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        purchaseRepository.deleteAll();
        userRepository.deleteAll();
    }

    @AfterEach
    void tearDown(){
        // clear the database after each test
        purchaseRepository.deleteAll();
        userRepository.deleteAll();
    }



    @Test
    public void getPurchases_Success() throws Exception {
        //Arrange
        //Save user in DB
//        User user = new User("calvin@gmail.com", "testCalvin", passwordEncoder.encode("goodpassword"));
        AuthenticationRequest request = new AuthenticationRequest("testCalvin@gmail.com", "testCalvin", "goodpassword");
        AuthenticationResponse response = authenticationService.register(request);
//        userRepository.save(user);



        //Set URI
        String id = "testCalvin";
        URI uri = new URI(baseUrl + port + "/api/purchases/" + id);
        List<String> seatIDs = new ArrayList<>();
        seatIDs.add("A1");
        Purchase purchase = new Purchase("testCalvin", "barbier1", seatIDs);
        purchaseRepository.save(purchase);

        //Act
        //Authenticate user
//        AuthenticationRequest request = new AuthenticationRequest("testCalvin", "goodpassword");
//        AuthenticationResponse response = authenticationService.authenticate(request);

        //Set token in http header
        String token = response.getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        //Call API
        ResponseEntity<Purchase[]> result = testRestTemplate.exchange(uri, HttpMethod.GET, entity, Purchase[].class);
//        ResponseEntity<Purchase[]> result = testRestTemplate.getForEntity(uri, Purchase[].class);
        Purchase[] purchases = result.getBody();

        //Assert
        assertEquals(200, result.getStatusCode().value());
        assertEquals(1, purchases.length);
    }
}
