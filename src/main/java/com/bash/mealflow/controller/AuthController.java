package com.bash.mealflow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @GetMapping("/access-denied")
    public String accessDenied(){
        return "access-denied";
    }
    @GetMapping("/")
    public String home(){
        return "redirect:user/dashboard";
    }
}
