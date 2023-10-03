package com.cs203.TicketWarrior.Registration.services;

import com.cs203.TicketWarrior.Registration.models.User;
import com.cs203.TicketWarrior.Registration.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    private AutoCloseable autoCloseable;
    private UserService userServiceTest;

    @BeforeEach
    void setUp() {
        //Initialise all mocks into this test class
        autoCloseable = MockitoAnnotations.openMocks(this);
        userServiceTest = new UserService(userRepository);
    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    @Test
    void testDoesUsernameExist_UsernameExists() {
        //Arrange
        String existingUsername = "existingUser";
        when(userRepository.findUserByUsername(existingUsername)).thenReturn(Optional.of(new User()));

        //Act
        boolean result = userServiceTest.doesUsernameExist(existingUsername);

        //Assert
        assertTrue(result);
        verify(userRepository, times(1)).findUserByUsername(existingUsername);
    }

    @Test
    void testDoesUsernameExist_UsernameDoesNotExists() {
        //Arrange
        String nonExistingUsername = "nonExistingUser";
        when(userRepository.findUserByUsername(nonExistingUsername)).thenReturn(Optional.empty());

        //Act
        boolean result = userServiceTest.doesUsernameExist(nonExistingUsername);

        //Assert
        assertFalse(result);
        verify(userRepository, times(1)).findUserByUsername(nonExistingUsername);
    }

    @Test
    void doesEmailExist() {
    }

    @Test
    void doesUserIdExist() {
    }

    @Test
    void save() {
    }

    @Test
    void findUserByUsername() {
    }
}