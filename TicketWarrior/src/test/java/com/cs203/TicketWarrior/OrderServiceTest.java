package com.cs203.TicketWarrior;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cs203.TicketWarrior.Registration.repository.OrderRepository;
import com.cs203.TicketWarrior.Registration.services.OrderService;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void listOrders_anyInput_ReturnListOfOrders() {

    }
}
