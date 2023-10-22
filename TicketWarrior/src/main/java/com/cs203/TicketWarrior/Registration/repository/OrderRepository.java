 package com.cs203.TicketWarrior.Registration.repository;

 import java.util.List;
 import java.util.Optional;

// import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.data.mongodb.repository.MongoRepository;
 import com.cs203.TicketWarrior.Registration.models.Order;

public interface OrderRepository extends MongoRepository<Order, String> /*JpaRepository<Order, String>*/ {

    List<Order> findByUserId(String userId);

    Optional<Order> findByIdAndUserId(String id, String userId);
}
