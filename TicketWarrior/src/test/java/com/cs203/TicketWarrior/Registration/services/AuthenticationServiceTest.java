package com.cs203.TicketWarrior.Registration.services;

import com.cs203.TicketWarrior.Registration.jwt.JwtService;
import com.cs203.TicketWarrior.Registration.models.ERole;
import com.cs203.TicketWarrior.Registration.models.User;
import com.cs203.TicketWarrior.Registration.payload.AuthenticationRequest;
import com.cs203.TicketWarrior.Registration.payload.AuthenticationResponse;
import com.cs203.TicketWarrior.Registration.payload.RegisterRequest;
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
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
        // Initialise authentication service test to inject mocks
        authenticationServiceTest = new AuthenticationServiceimpl
                (userServiceimpl, passwordEncoder, jwtService, authenticationManager);
    }

    @Test
    void testCanRegister_Success() {
        //Arrange
        RegisterRequest registerRequest = new RegisterRequest("goodEmail@gmail.com", "goodUsername", "goodPassword1");
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
        RegisterRequest registerRequest = new RegisterRequest("badEmail@gmail.com", "badUsername", "badPassword1");
        when(userServiceimpl.doesUsernameExist(registerRequest.getUsername())).thenReturn(true);

        //Act
        AuthenticationResponse registerResponse = authenticationServiceTest.register(registerRequest);

        //Assert
        assertFalse(registerResponse.getIsSuccessful());
    }

    @Test
    void testAuthenticate_Success() {
        //Arrange
        AuthenticationRequest loginRequest = new AuthenticationRequest("goodUsername", "goodPassword1");
        User user = new User("goodEmail@gmail.com", loginRequest.getUsername(), "encodedPassword");
        user.setRole(ERole.USER);
        when(authenticationManager.authenticate(any())).thenReturn(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        when(userServiceimpl.findUserByUsername(loginRequest.getUsername())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("generatedToken");

        //Act
        AuthenticationResponse loginResponse = authenticationServiceTest.authenticate(loginRequest);

        //Assert
        assertTrue(loginResponse.getIsSuccessful());
        assertEquals("generatedToken", loginResponse.getToken());
    }

    @Test
    void testAuthenticate_Fail() {
        //Arrange
        AuthenticationRequest loginRequest = new AuthenticationRequest("badUsername", "badPassword1");
        when(authenticationManager.authenticate(any())).thenThrow(BadCredentialsException.class);

        //Act
        AuthenticationResponse loginResponse = authenticationServiceTest.authenticate(loginRequest);

        //Assert
        assertFalse(loginResponse.getIsSuccessful());
        assertNull(loginResponse.getToken());
    }
}
