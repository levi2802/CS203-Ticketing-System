package com.cs203.TicketWarrior.Registration.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.concurrent.atomic.AtomicBoolean;

@Data
@NoArgsConstructor
@Document(collection = "tickets")
public class Ticket {

    @Id
    private String id;

    private AtomicBoolean locked = new AtomicBoolean(false);

    public boolean isLocked() {
        return locked.get();
    }

    public boolean tryLock() {
        return locked.compareAndSet(false, true);
    }

}
