package com.example.auth_service.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class UserRegisterDto {
    public String username;
    public String phoneno;
    public String password;
    public String confirmPassword;
}
