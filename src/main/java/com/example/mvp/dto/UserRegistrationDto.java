package com.example.mvp.dto;

import com.example.mvp.enums.Major;
import com.example.mvp.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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

    // ADD THESE NEW FIELDS
    private String courseCode;
    private String courseName;
    private String yearLevel;

    @NotNull(message = "Terms acceptance is required")
    private Boolean termsAccepted;

    @NotNull(message = "Privacy acceptance is required")
    private Boolean privacyAccepted;

    // Constructors
    public UserRegistrationDto() {}

    // Existing getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Major getMajor() { return major; }
    public void setMajor(Major major) { this.major = major; }

    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }

    // ADD THESE NEW GETTERS AND SETTERS
    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public String getYearLevel() { return yearLevel; }
    public void setYearLevel(String yearLevel) { this.yearLevel = yearLevel; }

    public Boolean getTermsAccepted() { return termsAccepted; }
    public void setTermsAccepted(Boolean termsAccepted) { this.termsAccepted = termsAccepted; }

    public Boolean getPrivacyAccepted() { return privacyAccepted; }
    public void setPrivacyAccepted(Boolean privacyAccepted) { this.privacyAccepted = privacyAccepted; }
}