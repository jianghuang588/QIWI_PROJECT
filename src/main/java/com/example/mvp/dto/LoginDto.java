package com.example.mvp.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginDto {
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    // Constructors
    public LoginDto() {}

    public LoginDto(String email) {
        this.email = email;
    }

    // Getters and Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}