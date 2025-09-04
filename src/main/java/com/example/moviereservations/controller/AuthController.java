package com.example.moviereservations.controller;

import com.example.moviereservations.model.User;
import com.example.moviereservations.service.AuthService;
import com.example.moviereservations.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username,
                            @RequestParam String password,
                            Model model,
                            HttpSession session) {
        try {
            String token = authService.login(username, password);
            session.setAttribute("JWT_TOKEN", token);
            return "redirect:/movies";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user,
                               BindingResult result, Model model) {
        if (result.hasErrors()) return "register";
        try {
            authService.register(user.getUsername(), user.getEmail(), user.getPassword());
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
        return "redirect:/login";
    }
}