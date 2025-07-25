package com.bash.mealflow.service;

import com.bash.mealflow.model.User;
import com.bash.mealflow.repository.UserRepositroy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepositroy userRepositroy;

    public Optional<User> findByUsername(String username) {
        return userRepositroy.findByUsername(username);
    }
    public User saveUser(User user) {
        return userRepositroy.save(user);
    }
}
