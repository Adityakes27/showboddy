package com.showbuddy.movie_booking.controller;

import com.showbuddy.movie_booking.model.User;
import com.showbuddy.movie_booking.repository.UserRepository;
import com.showbuddy.movie_booking.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signup")
    public Map<String, Object> signup(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();

        Optional<User> existing = userRepository.findAll().stream()
                .filter(u -> u.getEmail().equals(user.getEmail()))
                .findFirst();

        if (existing.isPresent()) {
            response.put("success", false);
            response.put("message", "Email already registered");
            return response;
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        String token = jwtUtil.generateToken(savedUser.getId(), savedUser.getEmail());

        response.put("success", true);
        response.put("token", token);
        response.put("userId", savedUser.getId());
        response.put("name", savedUser.getName());
        return response;
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> credentials) {
        Map<String, Object> response = new HashMap<>();
        String email = credentials.get("email");
        String password = credentials.get("password");

        Optional<User> userOpt = userRepository.findAll().stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst();

        if (userOpt.isEmpty() || !passwordEncoder.matches(password, userOpt.get().getPassword())) {
            response.put("success", false);
            response.put("message", "Invalid email or password");
            return response;
        }

        User user = userOpt.get();
        String token = jwtUtil.generateToken(user.getId(), user.getEmail());

        response.put("success", true);
        response.put("token", token);
        response.put("userId", user.getId());
        response.put("name", user.getName());
        return response;
    }
}