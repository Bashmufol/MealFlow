package com.bash.mealflow.service;

import com.bash.mealflow.dto.UserRegistrationRequest;
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

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Transactional // Ensures the entire method executes within a single transaction.
    public void registerNewUser(UserRegistrationRequest registrationRequest) {
        String username = registrationRequest.getUsername();
        String email = registrationRequest.getEmail();
        String password = registrationRequest.getPassword();
        String confirmPassword = registrationRequest.getConfirmPassword();

        // Validate if username or email already exist.
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username '" + username + "' already exists.");
        }
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email '" + email + "' already exists.");
        }
        // Validate password match.
        if (password == null || !password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Passwords do not match.");
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        // Encode password before saving for security.
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setRoles(Role.USER); // Assign default role for new users.

        userRepository.save(newUser);
    }
}