package com.cs203.TicketWarrior.Registration.servicesimpl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import com.cs203.TicketWarrior.Registration.services.CaptchaService;

@Service
public class CaptchaServiceImpl implements CaptchaService {

    // Constants for response messages
    private static final String TOKEN_MISSING_MSG = "Token is missing";
    private static final String VERIFICATION_ERROR_MSG = "Error verifying reCAPTCHA";
    private static final String ROBOT_MSG = "Robot 🤖";
    private static final String HUMAN_MSG = "Human 👨 👩";

    // Reads the secret key from application.properties file and stores it in var called recaptchaSecret.
    @Value("${recaptcha.secret}")
    private String recaptchaSecret;

    // Create an instance of object Mapper for JSON parsing.
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Spring's RestTemplate for making HTTP requests.
    private final RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<String> verifyCaptcha(String tokenJson) {
        try {
            // Extract the token using a helper method
            Optional<String> token = extractToken(tokenJson);

            // Check if the token is present, if not, return an error with HTTP 400 bad request.
            if (token.isPresent()) {
                return verifyToken(token.get());
            } else {
                return new ResponseEntity<>(TOKEN_MISSING_MSG, HttpStatus.BAD_REQUEST);
            }
        } catch (IOException e) {
            // Log the exception (This can be improved with a proper logger)
            return new ResponseEntity<>(VERIFICATION_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Helper method to extract token from JSON
    private Optional<String> extractToken(String tokenJson) throws IOException {
        JsonNode jsonNode = objectMapper.readTree(tokenJson);
        JsonNode tokenNode = jsonNode.get("token");
        return Optional.ofNullable(tokenNode).map(JsonNode::asText);
    }

    // Method to verify the token
    private ResponseEntity<String> verifyToken(String token) {
        try {
            // URL encode the token and construct the verification URL
            String encodedToken = URLEncoder.encode(token, StandardCharsets.UTF_8);
            String verificationUrl = buildVerificationUrl(encodedToken);

            // Make a POST request to verify the token
            String response = restTemplate.postForObject(verificationUrl, null, String.class);

            // Parse the response to check if verification was successful
            JsonNode jsonNode = objectMapper.readTree(response);
            JsonNode successNode = jsonNode.get("success");

            // Return appropriate response based on the verification result
            return successNode.asBoolean()
                    ? new ResponseEntity<>(HUMAN_MSG, HttpStatus.OK)
                    : new ResponseEntity<>(ROBOT_MSG, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            // Log the exception (This can be improved with a proper logger)
            return new ResponseEntity<>(VERIFICATION_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Helper method to build the verification URL
    private String buildVerificationUrl(String encodedToken) {
        return "https://www.google.com/recaptcha/api/siteverify?secret=" + recaptchaSecret + "&response=" + encodedToken;
    }
}
