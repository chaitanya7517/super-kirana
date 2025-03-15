package com.example.auth_service.service;

import com.example.auth_service.entity.User;
import com.example.auth_service.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    public ResponseEntity<?> getProfile(String username) {
        User user = userRepo.findByUsername(username).orElse(null);
        if(user==null) {
            return ResponseEntity.badRequest().body("Invalid user");
        }
        return ResponseEntity.ok().body(user);
    }

    public ResponseEntity<?> updateProfile(String username, Map<String, String> reqDto) {
        User user = userRepo.findByUsername(username).orElse(null);
        if(user==null) {
            return ResponseEntity.badRequest().body("Invalid user");
        }
        reqDto.forEach((key, value) -> {
            if(key.equals("firstname"))
                user.setFirstname(value);
            else if(key.equals("lastname"))
                user.setLastname(value);
            else if(key.equals("email"))
                user.setEmail(value);
            else if(key.equals("profileImg"))
                user.setProfileImg(value);
        });

        userRepo.save(user);
        return ResponseEntity.ok(user);
    }
}
