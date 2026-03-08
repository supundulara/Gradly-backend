package com.Gradly.user_service.controllers;

import com.Gradly.user_service.security.JwtUtil;
import com.Gradly.user_service.dto.AuthRequest;
import com.Gradly.user_service.dto.AuthResponse;
import com.Gradly.user_service.dto.RegisterRequest;
import com.Gradly.user_service.model.User;
import com.Gradly.user_service.repository.UserRepository;
import com.Gradly.user_service.service.AuthService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {

        authService.register(request);

        return "User registered successfully";
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {

        String token = authService.login(request);

        return new AuthResponse(token);
    }
}

