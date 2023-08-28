package kh.dev.movies.Registration;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

//    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User registerNewUser(String username, String plainPassword) {
        HashingAlgorithm algo = new HashingAlgorithm();
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(algo.hashStringUsingSHA256(plainPassword));
        return userRepository.save(newUser);
    }
}

