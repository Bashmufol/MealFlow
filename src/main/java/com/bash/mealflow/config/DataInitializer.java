package com.bash.mealflow.config;

import com.bash.mealflow.model.MenuItem;
import com.bash.mealflow.model.Role;
import com.bash.mealflow.model.User;
import com.bash.mealflow.repository.MenuItemRepository;
import com.bash.mealflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MenuItemRepository menuItemRepository; // Inject MenuItemRepository

    @Override
    public void run(String... args) throws Exception {
        // Initialize default admin user if not present
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

        // Initialize default employee user if not present
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

        // Initialize default menu items if none are present
        if (menuItemRepository.count() == 0) { // Check if any menu items exist
            System.out.println("No menu items found. Pre-seeding default menu items...");

            // Initialize menu items
            MenuItem jollofRice = new MenuItem();
            jollofRice.setName("Jollof Rice & Chicken");
            jollofRice.setDescription("Smoky jollof rice served with grilled chicken drumstick.");
            jollofRice.setPrice(new BigDecimal("1800.00"));
            jollofRice.setAvailableDate(LocalDate.now());
            jollofRice.setIsAvailable(true);
            menuItemRepository.save(jollofRice);

            MenuItem poundedYam = new MenuItem();
            poundedYam.setName("Pounded Yam & Egusi Soup");
            poundedYam.setDescription("Smooth pounded yam served with rich egusi soup and assorted meat.");
            poundedYam.setPrice(new BigDecimal("2500.00"));
            poundedYam.setAvailableDate(LocalDate.now());
            poundedYam.setIsAvailable(true);
            menuItemRepository.save(poundedYam);

            MenuItem ebaEfo = new MenuItem();
            ebaEfo.setName("Eba & Efo Riro");
            ebaEfo.setDescription("Garri (Eba) served with nutritious efo riro (spinach stew) and fish.");
            ebaEfo.setPrice(new BigDecimal("1500.00"));
            ebaEfo.setAvailableDate(LocalDate.now());
            ebaEfo.setIsAvailable(true);
            menuItemRepository.save(ebaEfo);

            MenuItem amalaGbegiri = new MenuItem();
            amalaGbegiri.setName("Amala, Gbegiri & Ewedu");
            amalaGbegiri.setDescription("Soft Amala served with traditional Gbegiri (beans soup) and Ewedu (jute leaves soup), with beef.");
            amalaGbegiri.setPrice(new BigDecimal("2200.00"));
            amalaGbegiri.setAvailableDate(LocalDate.now());
            amalaGbegiri.setIsAvailable(true);
            menuItemRepository.save(amalaGbegiri);

            MenuItem friedRice = new MenuItem();
            friedRice.setName("Fried Rice & Turkey");
            friedRice.setDescription("Flavorful fried rice with mixed vegetables and a piece of fried turkey.");
            friedRice.setPrice(new BigDecimal("2000.00"));
            friedRice.setAvailableDate(LocalDate.now());
            friedRice.setIsAvailable(true);
            menuItemRepository.save(friedRice);

            MenuItem indomie = new MenuItem();
            indomie.setName("Indomie & Egg");
            indomie.setDescription("Stir-fried instant noodles with vegetables and a fried egg.");
            indomie.setPrice(new BigDecimal("1000.00"));
            indomie.setAvailableDate(LocalDate.now());
            indomie.setIsAvailable(true);
            menuItemRepository.save(indomie);

            MenuItem moimoi = new MenuItem();
            moimoi.setName("Moi-Moi & Pap");
            moimoi.setDescription("Steamed bean pudding (Moi-Moi) served with smooth custard (Pap).");
            moimoi.setPrice(new BigDecimal("800.00"));
            moimoi.setAvailableDate(LocalDate.now());
            moimoi.setIsAvailable(true);
            menuItemRepository.save(moimoi);

            System.out.println("Default menu items pre-seeded successfully!");
        } else {
            System.out.println("Menu items already exist. Skipping pre-seeding.");
        }
    }
}