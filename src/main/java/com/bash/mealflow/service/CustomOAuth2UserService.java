package com.bash.mealflow.service;

import com.bash.mealflow.model.Role;
import com.bash.mealflow.model.User;
import com.bash.mealflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // Load the user from the OAuth2 provider (e.g., Google)
        OAuth2User oauth2User = super.loadUser(userRequest);

        String email = oauth2User.getAttribute("email");
        String name = oauth2User.getAttribute("name"); // Display name from Google

        Optional<User> userOptional = userRepository.findByEmail(email);
        User user;

        if (userOptional.isEmpty()) {
            // New OAuth2 user, provision a new User in our database
            user = new User();
            user.setEmail(email);
            // Generate a unique username based on email or name, ensure it's unique
            // For simplicity, using a sanitized version of name or a UUID
            String baseUsername = (name != null && !name.isEmpty()) ? name.replaceAll("\\s+", "").toLowerCase() : email.substring(0, email.indexOf("@"));
            String finalUsername = baseUsername;
            int counter = 0;
            while (userRepository.findByUsername(finalUsername).isPresent()) {
                counter++;
                finalUsername = baseUsername + counter;
            }
            user.setUsername(finalUsername);

            // For OAuth2 users, password is not managed by us, set an empty/dummy password
            // Spring Security handles authentication via OAuth2 provider
            user.setPassword("");
            user.setRoles(Role.USER); // Default role for new OAuth2 users
            userRepository.save(user);
        } else {
            // Existing user, update their details if necessary
            user = userOptional.get();
            // You might want to update the username if the Google name changes, etc.
            // user.setUsername(name != null ? name : user.getUsername());
            userRepository.save(user); // Save to ensure any updates are persisted
        }

        // Return a UserPrincipal that holds both our User entity and OAuth2 attributes
        return new UserPrincipal(user, oauth2User.getAttributes());
    }
}