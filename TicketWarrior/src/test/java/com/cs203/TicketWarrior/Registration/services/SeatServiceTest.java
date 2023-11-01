package com.cs203.TicketWarrior.Registration.services;

import com.cs203.TicketWarrior.Registration.models.Seat;
import com.cs203.TicketWarrior.Registration.repository.SeatRepository;
import com.cs203.TicketWarrior.Registration.servicesimpl.SeatServiceimpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SeatServiceTest {
    @Mock
    private SeatRepository seatRepository;
    private SeatServiceimpl seatServiceTest;

    @BeforeEach
    void setUp(){
        seatServiceTest = new SeatServiceimpl(seatRepository);
    }

    @Test
    void testDoesUsernameExist_UsernameTrue() {
        // Arrange
        String existingUsername = "existingUser";
        Seat existingUser = new Seat();
        existingUser.setUsername(existingUsername);
        when(seatRepository.findbyusername(existingUsername)).thenReturn(List.of(existingUser));

        // Act
        List<Seat> result = seatServiceTest.findbyID(existingUsername);

        // Assert
        assertFalse(result.isEmpty());
        for (Seat s : result) {
            assertEquals(existingUsername, s.getUsername());
        }
        verify(seatRepository, times(1)).findbyusername(existingUsername);
    }

    @Test
    void testInsert() {
        // Define a list of test cases
        List<Seat> testCases = Arrays.asList(
            new Seat(1, 1, "Standard", false, "existingUser", "Barbie"),
            new Seat(2, 2, "Standard", false, "user2", "Barbie")
        );

        for (Seat testCase : testCases) {
            // Arrange
            when(seatRepository.findById(testCase.getId())).thenReturn(Optional.of(testCase));

            // Act
            Optional<Seat> result = seatServiceTest.findSeatById(testCase.getId());

            // Assert
            assertTrue(result.isPresent()); // Check that a result is present
            assertEquals(testCase, result.get()); // Check that the result matches the expected test case
        }
    }

    @Test
    void testFindBySeat() {
        // Define a list of test cases
        List<Seat> testCases = Arrays.asList(
            new Seat(1, 1, "Standard", false, "existingUser", "Barbie"),
            new Seat(2, 2, "Standard", false, "user2", "Barbie")
        );
        testCases.get(0).setLocation("Jurong theatre");
        testCases.get(0).setTiming("11:00");
        testCases.get(1).setLocation("Hougang theatre");
        testCases.get(1).setTiming("09:00");
        // Arrange
        when(seatRepository.findAllOccupiedSeats("Barbie","Jurong theatre", "11:00")).thenReturn(Collections.singletonList(testCases.get(0)));

        // Act
        List<Seat> result = seatServiceTest.findAllOccupiedSeats(testCases.get(0).getMovieName(), testCases.get(0).getLocation(), testCases.get(0).getTiming());
        // Assert
        assertTrue(result.size() == 1); // Check that a result is present
        assertEquals(testCases.get(0), result.get(0)); // Check that the result matches the expected test case
    }
}
