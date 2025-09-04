package com.example.moviereservations.controller;

import com.example.moviereservations.model.User;
import com.example.moviereservations.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "index"; // show login/register selection
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "register";
        }
        if (userService.existsByUsername(user.getUsername())) {
            model.addAttribute("usernameError", "Username already taken");
            return "register";
        }
        if (userService.existsByEmail(user.getEmail())) {
            model.addAttribute("emailError", "Email already registered");
            return "register";
        }

        userService.registerUser(user);
        return "redirect:/login";
    }
}
