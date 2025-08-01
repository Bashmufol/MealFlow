package com.bash.mealflow.config;

import com.bash.mealflow.model.Role;
import com.bash.mealflow.model.User;
import com.bash.mealflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByUsername("admin").isEmpty()) {
            System.out.println("No admin found. Creating default admin...");

            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setEmail("admin@mealflow.com");
            adminUser.setPassword(passwordEncoder.encode("adminPass"));
            adminUser.setRoles(Role.ADMIN);

            userRepository.save(adminUser);
            System.out.println("Default admin created successfully!");
        } else {
            System.out.println("Admin already exists. Skipping creation.");
        }

        if (userRepository.findByUsername("user").isEmpty()) {
            System.out.println("No employee user found. Creating default employee user...");

            User employeeUser = new User();
            employeeUser.setUsername("user");
            employeeUser.setEmail("employee@mealflow.com");
            employeeUser.setPassword(passwordEncoder.encode("password"));
            employeeUser.setRoles(Role.USER);

            userRepository.save(employeeUser);
            System.out.println("Default user created successfully!");
        } else {
            System.out.println("User already exists. Skipping creation.");
        }


    }
}
