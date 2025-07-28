package com.bash.mealflow.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
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
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String redirectURL = "/";
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        if (authorities.stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ADMIN")) ) {
            redirectURL = "/admin/dashboard";
        }
        else if(authorities.stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("USER")) ) {
            redirectURL = "/user/dashboard";
        }
        else {
            redirectURL = "/access-denied";
        }
        response.sendRedirect(request.getContextPath() + redirectURL);
    }
}
