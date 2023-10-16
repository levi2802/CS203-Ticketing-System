package com.cs203.TicketWarrior.Registration.services;

import com.cs203.TicketWarrior.Registration.jwt.JwtService;
import com.cs203.TicketWarrior.Registration.models.ERole;
import com.cs203.TicketWarrior.Registration.models.User;
import com.cs203.TicketWarrior.Registration.payload.AuthenticationRequest;
import com.cs203.TicketWarrior.Registration.payload.AuthenticationResponse;
import com.cs203.TicketWarrior.Registration.repository.UserRepository;
import com.cs203.TicketWarrior.Registration.servicesimpl.AuthenticationServiceimpl;
import com.cs203.TicketWarrior.Registration.servicesimpl.UserServiceimpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserServiceimpl userServiceimpl;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    private AuthenticationServiceimpl authenticationServiceTest;


    @BeforeEach
    void setUp() {
        authenticationServiceTest = new AuthenticationServiceimpl
                (userServiceimpl, passwordEncoder, jwtService, authenticationManager);
    }

    @Test
    void testCanRegister_Success() {
        //Arrange
        AuthenticationRequest registerRequest = new AuthenticationRequest("goodEmail@gmail.com", "goodUsername", "goodPassword1");
        User user = new User(registerRequest.getEmail(), registerRequest.getUsername(), "encodedPassword");
        user.setRole(ERole.USER);
        when(userServiceimpl.doesUsernameExist(registerRequest.getUsername())).thenReturn(false);
        when(userServiceimpl.doesEmailExist(registerRequest.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("encodedPassword");
        when(jwtService.generateToken(user)).thenReturn("generatedToken");

        //Act
        AuthenticationResponse registerResponse = authenticationServiceTest.register(registerRequest);

        //Assert
        assertTrue(registerResponse.getIsSuccessful());
        verify(userServiceimpl, times(1)).save(any());
    }

    @Test
    void testCanRegister_Fail() {
        //Arrange
        AuthenticationRequest registerRequest = new AuthenticationRequest("badEmail@gmail.com", "badUsername", "badPassword1");
        when(userServiceimpl.doesUsernameExist(registerRequest.getUsername())).thenReturn(true);

        //Act
        AuthenticationResponse registerResponse = authenticationServiceTest.register(registerRequest);

        //Assert
        assertFalse(registerResponse.getIsSuccessful());
    }
}
