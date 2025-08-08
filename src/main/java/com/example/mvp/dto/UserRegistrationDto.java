package com.example.mvp.dto;

import com.example.mvp.enums.Major;
import com.example.mvp.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

// User Registration DTO
public class UserRegistrationDto {
    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotNull(message = "Major is required")
    private Major major;

    @NotNull(message = "Role is required")
    private UserRole role;

    @NotNull(message = "Terms acceptance is required")
    private Boolean termsAccepted;

    @NotNull(message = "Privacy acceptance is required")
    private Boolean privacyAccepted;

    // Constructors, getters, setters
    public UserRegistrationDto() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Major getMajor() { return major; }
    public void setMajor(Major major) { this.major = major; }

    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }

    public Boolean getTermsAccepted() { return termsAccepted; }
    public void setTermsAccepted(Boolean termsAccepted) { this.termsAccepted = termsAccepted; }

    public Boolean getPrivacyAccepted() { return privacyAccepted; }
    public void setPrivacyAccepted(Boolean privacyAccepted) { this.privacyAccepted = privacyAccepted; }
}
