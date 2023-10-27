package com.cs203.TicketWarrior.Registration.services;

import java.util.Optional;

import com.cs203.TicketWarrior.Registration.models.User;

public interface UserService {
    public Boolean doesUsernameExist(String username);

    public Boolean doesEmailExist(String email);

    public Boolean doesUserIdExist(String userId);

    public void save(User user);

    public Optional<User> findUserByUsername(String username);
}
