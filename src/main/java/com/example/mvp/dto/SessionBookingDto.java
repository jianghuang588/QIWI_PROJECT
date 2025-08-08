package com.example.mvp.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class SessionBookingDto {
    @NotNull(message = "Tutor ID is required")
    private Long tutorId;

    @NotNull(message = "Student ID is required")
    private Long studentId;

    @NotBlank(message = "Subject is required")
    private String subject;

    @NotNull(message = "Start time is required")
    private LocalDateTime startTime;

    @NotNull(message = "End time is required")
    private LocalDateTime endTime;

    private String studentNotes;
    private String location;

    public SessionBookingDto() {}

    // Getters and Setters
    public Long getTutorId() { return tutorId; }
    public void setTutorId(Long tutorId) { this.tutorId = tutorId; }

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    public String getStudentNotes() { return studentNotes; }
    public void setStudentNotes(String studentNotes) { this.studentNotes = studentNotes; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}
