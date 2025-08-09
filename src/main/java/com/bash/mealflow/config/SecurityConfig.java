package com.bash.mealflow.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorize -> authorize
                        // Allow unauthenticated access to static resources and public pages.
                        .requestMatchers("/css/**", "/js/**", "/", "/webjars/**", "/images/**", "/login", "/register").permitAll()
                        // Define access rules for user and admin specific paths.
                        .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        // All other requests require authentication.
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        // Custom handler for dynamic post-login redirection based on roles.
                        .successHandler(customAuthenticationSuccessHandler)
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll())
                .exceptionHandling(exception -> exception
                        // Redirect to a custom page on access denied.
                        .accessDeniedPage("/access-denied"))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCrypt for secure password hashing.
        return new BCryptPasswordEncoder();
    }
}