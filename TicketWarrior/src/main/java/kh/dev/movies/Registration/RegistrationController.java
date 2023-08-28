package kh.dev.movies.Registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    @Autowired
    private UserService userService;

    @PostMapping("/api/v1/register")
    public User registerUser(@RequestBody UserRegistrationDto registrationDto) {
        return userService.registerNewUser(registrationDto.getUsername(), registrationDto.getPassword());
    }
}

