package com.cs203.TicketWarrior.Registration.services;

import org.springframework.http.ResponseEntity;

public interface CaptchaService {
    public ResponseEntity<String> verifyCaptcha(String token);
}
