package com.cs203.TicketWarrior;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import ch.qos.logback.core.model.Model;

@RestController
public class UserController {
    
    public String showRegistration(WebRequest request, Model model) {
        UserDTO userdto = new UserDTO();
        return "registration";
    }
}
