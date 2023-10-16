package com.cs203.TicketWarrior.Registration.services;

import com.cs203.TicketWarrior.Registration.models.User;
import com.cs203.TicketWarrior.Registration.repository.UserRepository;
import com.cs203.TicketWarrior.Registration.servicesimpl.UserServiceimpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    private UserServiceimpl userServiceTest;

    @BeforeEach
    void setUp() {
        userServiceTest = new UserServiceimpl(userRepository);
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
    void testDoesEmailExist_EmailExists() {
        //Arrange
        String existingEmail = "existingEmail@gmail.com";
        when(userRepository.findUserByEmail(existingEmail)).thenReturn(Optional.of(new User()));

        //Act
        boolean result = userServiceTest.doesEmailExist(existingEmail);

        //Assert
        assertTrue(result);
        verify(userRepository, times(1)).findUserByEmail(existingEmail);
    }

    @Test
    void testDoesEmailExist_EmailDoesNotExists() {
        //Arrange
        String nonExistingEmail = "nonExistingEmail@gmail.com";
        when(userRepository.findUserByEmail(nonExistingEmail)).thenReturn(Optional.empty());

        //Act
        boolean result = userServiceTest.doesEmailExist(nonExistingEmail);

        //Assert
        assertFalse(result);
        verify(userRepository, times(1)).findUserByEmail(nonExistingEmail);
    }

    @Test
    void testDoesUserIdExist_IdExists() {
        //Arrange
        String existingId = "1";
        when(userRepository.findById(existingId)).thenReturn(Optional.of(new User()));

        //Act
        boolean result = userServiceTest.doesUserIdExist(existingId);

        //Assert
        assertTrue(result);
        verify(userRepository, times(1)).findById(existingId);
    }

    @Test
    void testDoesUserIdExist_IdDoesNotExists() {
        //Arrange
        String nonExistingId = "1";
        when(userRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        //Act
        boolean result = userServiceTest.doesUserIdExist(nonExistingId);

        //Assert
        assertFalse(result);
        verify(userRepository, times(1)).findById(nonExistingId);
    }

    @Test
    void testSave() {
        //Arrange
        User user = new User(
                "calvin@gmail.com",
                "calvin",
                "password"
        );

        //Act
        userServiceTest.save(user);

        //Assert
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        verify(userRepository).save(userArgumentCaptor.capture());

        User capturedUser = userArgumentCaptor.getValue();
        assertEquals(user, capturedUser);
    }

    @Test
    void testFindUserByUsername_UserExists_ReturnUser() {
        //Arrange
        String usernameToFind = "testUsername";
        User userToReturn = new User();
        when(userRepository.findUserByUsername(usernameToFind)).thenReturn(Optional.of(userToReturn));

        //Act
        Optional<User> result = userServiceTest.findUserByUsername(usernameToFind);

        //Assert
        assertTrue(result.isPresent());
        assertEquals(userToReturn, result.get());
        verify(userRepository).findUserByUsername(usernameToFind);

    }
}