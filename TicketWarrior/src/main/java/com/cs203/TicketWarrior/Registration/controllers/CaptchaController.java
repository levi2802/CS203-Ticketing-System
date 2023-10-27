package com.cs203.TicketWarrior.Registration.controllers;

import com.cs203.TicketWarrior.Registration.services.CaptchaService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/captcha")
public class CaptchaController {
    
    private final CaptchaService captchaService;

    @PostMapping("/verification")
    public ResponseEntity<String> verifyCaptcha(@RequestBody String tokenJson) {
        // tokenJson is taken as an input, expected to contain recaptcha token.
        try {
            // Check if the token is empty, if yes, throw an error with http 400 bad
            // request.
            if (tokenJson == null || tokenJson.isEmpty()) {
                return ResponseEntity.status(400).body("Token is missing");
            }

            // Verify the token
            return captchaService.verifyCaptcha(tokenJson);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body("Invalid token JSON format");
        }
    }

}
