package com.example.auth_service.service;

import com.example.auth_service.dto.UserLoginDto;
import com.example.auth_service.dto.UserRegisterDto;
import com.example.auth_service.entity.User;
import com.example.auth_service.repo.UserRepo;
import com.example.auth_service.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collections;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    UserRepo userRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtil jwtUtil;

    public ResponseEntity<?> register(@RequestBody UserRegisterDto userDto) {
        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Passwords do not match!");
        }

        Optional<User> existingUser = userRepo.findByUsername(userDto.getUsername());
        if (existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists!");
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setPhoneno(userDto.getPhoneno());

        userRepo.save(user);

        String token = jwtUtil.generateToken(user.getUsername());
        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }

    public ResponseEntity<?> login(@RequestBody UserLoginDto userLoginDto) {
        User existingUser = userRepo.findByUsername(userLoginDto.getUsername()).orElse(null);

        if (existingUser == null) {
            existingUser = userRepo.findByPhoneno(userLoginDto.getUsername()).orElse(null);
        }

        if (existingUser == null || !passwordEncoder.matches(userLoginDto.getPassword(), existingUser.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }

        String token = jwtUtil.generateToken(existingUser.getUsername());
        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }
}
