package com.example.auth_service.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class UserLoginDto {
    public String username;
    public String password;
}
