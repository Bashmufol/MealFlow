package com.bash.mealflow.controller;

import com.bash.mealflow.dto.UserRegistrationRequest;
import com.bash.mealflow.model.User;
import com.bash.mealflow.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService userService;

    // Disallowing certain fields directly from binding for security/integrity
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("id", "roles");
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userRegistrationRequest", new UserRegistrationRequest()); // Use DTO
        return "register";
    }

    @PostMapping("/register")
    public String registerUserAccount(@ModelAttribute("userRegistrationRequest") UserRegistrationRequest userRegistrationRequest,
                                      Model model,
                                      RedirectAttributes redirectAttributes) {
        try{
            // Service handles the mapping and validation
            userService.registerNewUser(userRegistrationRequest);
            redirectAttributes.addFlashAttribute("successMessage", "Registration Successful. Please log in.");
            return "redirect:/login";
        } catch (IllegalArgumentException e){
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("userRegistrationRequest", userRegistrationRequest); // Retain form data on error
            return "register";
        }
    }
}