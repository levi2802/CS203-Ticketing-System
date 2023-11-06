package com.cs203.TicketWarrior.Registration.IntegrationTests;

import com.cs203.TicketWarrior.Registration.models.Purchase;
import com.cs203.TicketWarrior.Registration.payload.AuthenticationRequest;
import com.cs203.TicketWarrior.Registration.payload.AuthenticationResponse;
import com.cs203.TicketWarrior.Registration.payload.RegisterRequest;
import com.cs203.TicketWarrior.Registration.repository.PurchaseRepository;
import com.cs203.TicketWarrior.Registration.repository.UserRepository;
import com.cs203.TicketWarrior.Registration.servicesimpl.AuthenticationServiceimpl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.net.URI;
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
    private AuthenticationServiceimpl authenticationService;

    @BeforeEach
    void setUp() {
        purchaseRepository.deleteAll();
        userRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        // clear the database after each test
        purchaseRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testGetPurchasesByUser_Success() throws Exception {
        // Arrange
        // Register user
        RegisterRequest request = new RegisterRequest("testCalvin@gmail.com", "testCalvin", "goodpassword1");
        AuthenticationResponse response = authenticationService.register(request);

        // Set URI
        String id = "testCalvin";
        URI uri = new URI(baseUrl + port + "/api/v1/purchases/" + id);
        String seatIDs = "A1";
        Purchase purchase = new Purchase("testCalvin", "barbier1", seatIDs);
        purchaseRepository.save(purchase);

        // Set token in http header
        String token = response.getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        // Act
        // Call API
        ResponseEntity<Purchase[]> result = testRestTemplate.exchange(uri, HttpMethod.GET, entity, Purchase[].class);
        Purchase[] purchases = result.getBody();

        // Assert
        assertEquals(200, result.getStatusCode().value());
        assertEquals(1, purchases.length);
    }

    @Test
    public void testGetPurchasesByUser_Fail() throws Exception {
        // Arrange
        // Register user
        RegisterRequest request = new RegisterRequest("testCalvin@gmail.com", "testCalvin", "goodpassword1");
        AuthenticationResponse response = authenticationService.register(request);

        // Set URI
        String id = "invalidUserName";
        URI uri = new URI(baseUrl + port + "/api/v1/purchases/" + id);
        String seatIDs = "A1";
        Purchase purchase = new Purchase("testCalvin", "barbier1", seatIDs);
        purchaseRepository.save(purchase);

        // Set token in http header
        String token = response.getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        // Act
        // Call API
        ResponseEntity<Purchase[]> result = testRestTemplate.exchange(uri, HttpMethod.GET, entity, Purchase[].class);
        Purchase[] purchases = result.getBody();

        // Assert
        assertEquals(200, result.getStatusCode().value());
        assertEquals(0, purchases.length);
    }

    @Test
    public void testCreatePurchase_Success() throws Exception {
        // Arrange
        // Register user
        RegisterRequest request = new RegisterRequest("testCalvin@gmail.com", "testCalvin", "goodpassword1");
        AuthenticationResponse response = authenticationService.register(request);

        // Set URI
        String id = "testCalvin";
        URI uri = new URI(baseUrl + port + "/api/v1/purchases");
        String seatIDs = "A1";
        Purchase purchase = new Purchase("testCalvin", "barbier1", seatIDs);

        // Set token in http header
        String token = response.getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<?> entity = new HttpEntity<>(purchase, headers);

        // Act
        // Call API
        ResponseEntity<Purchase> result = testRestTemplate.exchange(uri, HttpMethod.POST, entity, Purchase.class);
        Purchase purchases = result.getBody();

        // Assert
        assertEquals(201, result.getStatusCode().value());
        assertEquals(purchases.getUserId(), id);
    }

    @Test
    public void testCreatePurchase_Fail() throws Exception {
        // Arrange
        // Set URI
        String invalidUserName = "invalidUser";
        URI uri = new URI(baseUrl + port + "/api/v1/purchases");
        String seatIDs = "A1";
        Purchase purchase = new Purchase(invalidUserName, "barbier1", seatIDs);

        // Set token in http header
        String token = "invalidToken";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<?> entity = new HttpEntity<>(purchase, headers);

        // Act
        // Call API
        ResponseEntity<Purchase> result = testRestTemplate.exchange(uri, HttpMethod.POST, entity, Purchase.class);
        Purchase purchases = result.getBody();

        // Assert
        assertEquals(403, result.getStatusCode().value());
    }
}
