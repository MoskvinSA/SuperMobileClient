package com.example.autoservice.data;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginFormDto {
    private String username;
    private String password;
}