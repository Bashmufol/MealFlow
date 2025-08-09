package com.bash.mealflow.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException{
        String redirectURL = "/";
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        // Redirect based on user roles after successful authentication.
        if (authorities.stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN")) ) {
            redirectURL = "/admin/dashboard";
        }
        else if(authorities.stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_USER")) ) {
            redirectURL = "/user/dashboard";
        }
        else {
            redirectURL = "/access-denied"; // // Handle unexpected roles.
        }
        response.sendRedirect(request.getContextPath() + redirectURL);
    }
}