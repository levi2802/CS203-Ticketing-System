package com.cs203.TicketWarrior.Registration.services;

import com.cs203.TicketWarrior.Registration.TestConfig.TestConfig;
import com.cs203.TicketWarrior.Registration.models.Seat;
import com.cs203.TicketWarrior.Registration.repository.SeatRepository;
import com.cs203.TicketWarrior.Registration.servicesimpl.SeatServiceimpl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
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
    void testFindOccupiedSeats() {
        // Create a list of occupied seats for the first scenario
        List<Seat> occupiedSeats1 = new ArrayList<>();

        // Initially, you have one occupied seat with specific timing
        Seat firstOccupiedSeat = new Seat(1, 1, "Standard", false, "existingUser", "Barbie");
        firstOccupiedSeat.setLocation("Jurong theatre");
        firstOccupiedSeat.setTiming("11:00");
        occupiedSeats1.add(firstOccupiedSeat);

        Seat secondOccupiedSeat = new Seat(2, 2, "Standard", false, "existingUser", "Barbie");
        secondOccupiedSeat.setLocation("Jurong theatre");
        secondOccupiedSeat.setTiming("11:00");
        occupiedSeats1.add(secondOccupiedSeat);

        // Arrange and Act for the first scenario
        when(seatRepository.findAllOccupiedSeats("Barbie", "Jurong theatre", "11:00")).thenReturn(occupiedSeats1);

        List<Seat> result1 = seatServiceTest.findAllOccupiedSeats("Barbie", "Jurong theatre", "11:00");

        // Assert the first scenario
        assertEquals(2, result1.size());
        assertEquals(occupiedSeats1, result1);
        verify(seatRepository, times(1)).findAllOccupiedSeats("Barbie", "Jurong theatre", "11:00");

        // Create a new list of occupied seats for the second scenario
        List<Seat> occupiedSeats2 = new ArrayList<>();

        // Add a new occupied seat with different timing to the second list
        Seat newOccupiedSeat = new Seat(3, 3, "Standard", false, "user2", "Barbie");
        newOccupiedSeat.setLocation("Jurong theatre");
        newOccupiedSeat.setTiming("09:00");
        occupiedSeats2.add(newOccupiedSeat);

        // Arrange and Act for the second scenario
        when(seatRepository.findAllOccupiedSeats("Barbie", "Jurong theatre", "09:00")).thenReturn(occupiedSeats2);

        List<Seat> result2 = seatServiceTest.findAllOccupiedSeats("Barbie", "Jurong theatre", "09:00");

        // Assert the second scenario
        assertEquals(1, result2.size());
        assertEquals(occupiedSeats2, result2);
        verify(seatRepository, times(1)).findAllOccupiedSeats("Barbie", "Jurong theatre", "09:00");
    }

}
