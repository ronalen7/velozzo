package com.fooddelivery.auth_service.controller;

import com.fooddelivery.auth_service.dto.*;
import com.fooddelivery.auth_service.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
        log.debug("Registering user with email: {}", request.getEmail());
        authService.register(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        log.debug("Logging in user with email: {}", request.getEmail());
        String token = authService.login(request);
        log.debug("Login successful for email: {}", request.getEmail());
        return ResponseEntity.ok(new LoginResponse(token));
    }
}