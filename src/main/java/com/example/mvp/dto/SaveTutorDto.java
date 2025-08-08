package com.example.mvp.dto;



import jakarta.validation.constraints.NotNull;

public class SaveTutorDto {
    @NotNull(message = "Tutee ID is required")
    private Long tuteeId;

    @NotNull(message = "Tutor ID is required")
    private Long tutorId;

    // Constructors
    public SaveTutorDto() {}

    public SaveTutorDto(Long tuteeId, Long tutorId) {
        this.tuteeId = tuteeId;
        this.tutorId = tutorId;
    }

    // Getters and Setters
    public Long getTuteeId() { return tuteeId; }
    public void setTuteeId(Long tuteeId) { this.tuteeId = tuteeId; }

    public Long getTutorId() { return tutorId; }
    public void setTutorId(Long tutorId) { this.tutorId = tutorId; }
}