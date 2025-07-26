package com.bash.mealflow.service;

import com.bash.mealflow.model.Role;
import com.bash.mealflow.model.User;
import com.bash.mealflow.repository.UserRepositroy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepositroy userRepository;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public User saveUser(User user) {
        return userRepository.save(user);
    }
    public User registerUser(User user) {
        user.setRoles(Role.USER);
        return userRepository.save(user);
    }
}
