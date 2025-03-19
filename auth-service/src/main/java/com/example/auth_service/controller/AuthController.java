package com.example.auth_service.controller;

import com.example.auth_service.dto.UserLoginDto;
import com.example.auth_service.dto.UserRegisterDto;
import com.example.auth_service.entity.User;
import com.example.auth_service.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterDto userRegisterDto) {
        return authService.register(userRegisterDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDto userLoginDto) {
        return authService.login(userLoginDto);
    }

}