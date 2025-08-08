package com.example.mvp.dto;


import com.example.mvp.enums.Subject;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

public class TutorProfileDto {
    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Subjects are required")
    private List<Subject> subjects;

    @NotNull(message = "Grades are required")
    private Map<String, String> grades;

    @NotBlank(message = "Teaching approach is required")
    private String teachingApproach;

    public TutorProfileDto() {}

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public List<Subject> getSubjects() { return subjects; }
    public void setSubjects(List<Subject> subjects) { this.subjects = subjects; }

    public Map<String, String> getGrades() { return grades; }
    public void setGrades(Map<String, String> grades) { this.grades = grades; }

    public String getTeachingApproach() { return teachingApproach; }
    public void setTeachingApproach(String teachingApproach) { this.teachingApproach = teachingApproach; }
}
