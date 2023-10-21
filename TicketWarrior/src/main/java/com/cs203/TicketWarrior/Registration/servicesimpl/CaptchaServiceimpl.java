package com.cs203.TicketWarrior.Registration.servicesimpl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;

import com.cs203.TicketWarrior.Registration.services.CaptchaService;

@Service
public class CaptchaServiceimpl implements CaptchaService {
    // Reads the secret key from
    // application.properties file and
    // stores it in var call
    // recaptchaSecret.
    @Value("${recaptcha.secret}")
    private String recaptchaSecret;
    // Create an instance of object Mapper for json parsing.
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<String> verifyCaptcha(String tokenJson) {
        try {
            // URL Encoding helps to encode the token so that it can be safely included in a
            // URL.
            // Extract jsonNode as a json object
            JsonNode jsonNode = objectMapper.readTree(tokenJson);
            // Extract token as a string from the TextNode object
            String token = jsonNode.get("token").asText();

            String encodedToken = URLEncoder.encode(token, StandardCharsets.UTF_8);
            // Constructs the URL for Google's reCAPTCHA API.
            String verificationUrl = "https://www.google.com/recaptcha/api/siteverify?secret=" + recaptchaSecret
                    + "&response=" + encodedToken;
                    
            // Makes a POST request using Spring's RestTemplate.
            String response = restTemplate.postForObject(verificationUrl, null, String.class);

            // Extract response from google's api as a json object.
            JsonNode responseNode = objectMapper.readTree(response);

            // Checks the success field (BooleanNode) in the JSON response.
            JsonNode successNode = responseNode.get("success");

            // If true, it returns "Human 👨 👩". Otherwise, it logs the error codes and
            // returns "Robot 🤖".
            if (successNode != null && successNode.asBoolean()) {
                return ResponseEntity.ok("Human 👨 👩");
            } else {
                JsonNode errorCodes = responseNode.get("error-codes");
                System.out.println("Error Codes: " + errorCodes);
                return ResponseEntity.status(400).body("Robot 🤖");
            }

        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return ResponseEntity.status(500).body("Error verifying reCAPTCHA");
        }
    }
}
