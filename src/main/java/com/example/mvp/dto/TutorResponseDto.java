package com.example.mvp.dto;



import com.example.mvp.enums.Major;
import com.example.mvp.enums.Subject;

import java.util.List;
import java.util.Map;

public class TutorResponseDto {
    private Long id;
    private String name;
    private String email;
    private Major major;
    private List<Subject> subjects;
    private Map<String, String> grades;
    private String teachingApproach;

    // Constructors
    public TutorResponseDto() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Major getMajor() { return major; }
    public void setMajor(Major major) { this.major = major; }

    public List<Subject> getSubjects() { return subjects; }
    public void setSubjects(List<Subject> subjects) { this.subjects = subjects; }

    public Map<String, String> getGrades() { return grades; }
    public void setGrades(Map<String, String> grades) { this.grades = grades; }

    public String getTeachingApproach() { return teachingApproach; }
    public void setTeachingApproach(String teachingApproach) { this.teachingApproach = teachingApproach; }
}