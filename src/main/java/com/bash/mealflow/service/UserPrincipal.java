package com.bash.mealflow.service;

import com.bash.mealflow.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
public class UserPrincipal implements UserDetails, OAuth2User {
    private final User user;
    private Map<String, Object> attributes; // To store OAuth2 attributes

    // Constructor for OAuth2 users (from CustomOAuth2UserService)
    public UserPrincipal(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRoles().name()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // UserDetails standard methods, all set to true for simplicity
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // Getter for the underlying User entity
    public User getUser() {
        return user;
    }

    // OAuth2User methods
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        // This method is primarily used by Spring Security for identifying the principal.
        // For OAuth2, it's often the 'sub' claim or equivalent unique ID.
        // For our internal user, we can return the username or email.
        if (attributes != null && attributes.containsKey("sub")) {
            return (String) attributes.get("sub"); // Google's unique ID
        }
        return user.getUsername();
    }
}