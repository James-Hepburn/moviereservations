package com.example.moviereservations.service;

import com.example.moviereservations.model.Role;
import com.example.moviereservations.model.User;
import com.example.moviereservations.repository.UserRepository;
import com.example.moviereservations.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    private static final Role USER_ROLE = new Role ("ROLE_USER");
    private static final Role ADMIN_ROLE = new Role ("ROLE_ADMIN");

    public String login (String usernameOrEmail, String password) {
        try {
            this.authenticationManager.authenticate (new UsernamePasswordAuthenticationToken (usernameOrEmail, password));
            User user = this.userRepository.findByUsername (usernameOrEmail)
                    .orElse (this.userRepository.findByEmail (usernameOrEmail)
                            .orElseThrow (() -> new RuntimeException ("User not found")));
            return this.jwtUtil.generateToken (org.springframework.security.core.userdetails.User
                    .withUsername (user.getUsername ())
                    .password (user.getPassword ())
                    .authorities(user.getRoles().stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList()))
                    .build ());
        } catch (AuthenticationException e) {
            throw new RuntimeException ("Invalid credentials");
        }
    }

    public User register (String username, String email, String password) {
        if (this.userRepository.existsByUsername (username)) {
            throw new RuntimeException ("Username already exists");
        }else if (this.userRepository.existsByEmail (email)) {
            throw new RuntimeException ("Email already exists");
        }

        Set<String> roles = new HashSet<>();
        roles.add ("ROLE_USER");

        User user = new User (username, email, this.passwordEncoder.encode (password), roles);
        return this.userRepository.save (user);
    }

    public void promoteToAdmin (Long userId) {
        if (!this.userRepository.existsById (userId)) {
            throw new RuntimeException ("User not found");
        }

        User user = this.userRepository.findById (userId).get ();
        user.getRoles ().add ("ROLE_ADMIN");
        this.userRepository.save (user);
    }
}