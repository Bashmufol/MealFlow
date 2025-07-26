package com.bash.mealflow.service;


import com.bash.mealflow.model.User;
import com.bash.mealflow.repository.UserRepositroy;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepositroy userRepositroy;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepositroy.findByUsername(username);
        if(user.isPresent()){
            return new UserPrincipal(user.get());
        } else
            throw new UsernameNotFoundException("Cannot find the username: " + username);
    }
}
