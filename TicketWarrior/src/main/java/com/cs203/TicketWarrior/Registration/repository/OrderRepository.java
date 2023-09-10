package com.cs203.TicketWarrior.Registration.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cs203.TicketWarrior.Registration.models.Order;

public interface OrderRepository extends JpaRepository<Order, String> {

    List<Order> findByUserId(String userId);

    Optional<Order> findByIdAndUserId(String id, String userId);
}
