package com.cs203.TicketWarrior.Registration.services;

import com.cs203.TicketWarrior.Registration.models.User;
import org.springframework.stereotype.Service;
import com.cs203.TicketWarrior.Registration.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Boolean doesUsernameExist(String username) {
        Optional<User> user = userRepository.findUserByUsername(username);
        return user.isPresent();
    }

    public Boolean doesUserIdExist(String userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.isPresent();
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public Optional<User> findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public boolean doesEmailExist(String email) {
        Optional<User> user = userRepository.findUserByEmail(email);
        return user.isPresent();
    }
}
