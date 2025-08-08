package com.example.mvp.dto;

import com.example.mvp.enums.Subject;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class TuteeProfileDto {
    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Subjects needed are required")
    private List<Subject> subjectsNeeded;

    @NotBlank(message = "Personal introduction is required")
    private String personalIntro;

    // Constructors
    public TuteeProfileDto() {}

    // Getters and Setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public List<Subject> getSubjectsNeeded() { return subjectsNeeded; }
    public void setSubjectsNeeded(List<Subject> subjectsNeeded) { this.subjectsNeeded = subjectsNeeded; }

    public String getPersonalIntro() { return personalIntro; }
    public void setPersonalIntro(String personalIntro) { this.personalIntro = personalIntro; }
}