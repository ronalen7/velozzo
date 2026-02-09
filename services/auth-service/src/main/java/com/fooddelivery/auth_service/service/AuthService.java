package com.fooddelivery.auth_service.service;

import com.fooddelivery.auth_service.model.User;
import com.fooddelivery.auth_service.repository.UserRepository;
import com.fooddelivery.auth_service.dto.LoginRequest;
import com.fooddelivery.auth_service.dto.RegisterRequest;
import com.fooddelivery.auth_service.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

//    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public String register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        userRepository.save(user);
        return "User registered successfully";
    }

    public String login(LoginRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
        if (userOpt.isEmpty() || !passwordEncoder.matches(request.getPassword(), userOpt.get().getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }
//        return jwtUtil.generateToken(userOpt.get().getEmail(), userOpt.get().getRole());
        return jwtUtil.generateToken(userOpt.get().getEmail());
    }
}