package com.cs203.TicketWarrior.Registration.repository;

import com.cs203.TicketWarrior.Registration.TestConfig.TestConfig;
import com.cs203.TicketWarrior.Registration.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@Import(TestConfig.class)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepositoryTest;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @AfterEach
    void tearDown() {
        userRepositoryTest.deleteAll();
    }

    @Test
    void findUser_UsernameValidReturnUser_Success() {
        // Arrange
        User newUser = new User(
                "calvin@gmail.com",
                "calvin",
                passwordEncoder.encode("goodpassword1"));
        userRepositoryTest.save(newUser);

        // Act
        Optional<User> expectedUser = userRepositoryTest.findUserByUsername(newUser.getUsername());

        // Assert
        assertEquals(newUser, expectedUser.get());
    }

    @Test
    void findUser_UsernameInvalidReturnUser_Failure() {
        // Arrange
        User newUser = new User(
                "calvin@gmail.com",
                "calvin",
                passwordEncoder.encode("goodpassword1"));
        userRepositoryTest.save(newUser);

        // Act
        Optional<User> expectedUser = userRepositoryTest.findUserByUsername("invalid");

        // Assert
        assertTrue(expectedUser.isEmpty());
    }

    @Test
    void findUser_Email_ReturnUser() {
    }
}