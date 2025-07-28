package com.bash.mealflow.service;

import com.bash.mealflow.model.Role;
import com.bash.mealflow.model.User;
import com.bash.mealflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public User saveUser(User user) {
        return userRepository.save(user);
    }
    @Transactional
    public User registerNewUser(User user, String confirmPassword) {
        String username = user.getUsername();
        String email = user.getEmail();
        if(userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username already exists " + username);
        }
        if(userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email already exists " + email);
        }
        if(user.getPassword() == null || !user.getPassword().equals(confirmPassword)) {
            throw new IllegalArgumentException("Passwords do not match");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Role.USER);
        return userRepository.save(user);
    }
}
